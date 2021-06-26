package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.constants.*;
import com.gls.job.admin.core.route.ExecutorRouterHolder;
import com.gls.job.admin.core.support.CronExpression;
import com.gls.job.admin.core.support.RingDataHolder;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobAsyncService;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @author george
 */
@Slf4j
@Service("jobInfoService")
public class JobInfoServiceImpl implements JobInfoService {

    @Resource
    private JobAsyncService jobAsyncService;
    @Resource
    private JobGroupService jobGroupService;
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;
    @Resource
    private RingDataHolder ringDataHolder;
    @Resource
    private ExecutorRouterHolder executorRouterHolder;
    @Resource
    private ExecutorApiHolder executorApiHolder;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private JobInfoConverter jobInfoConverter;

    @Override
    public void doJobScheduleRing() {
        // second data
        List<Long> ringItemData = new ArrayList<>();
        // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        for (int i = 0; i < 2; i++) {
            List<Long> tmpData = ringDataHolder.remove((nowSecond + 60 - i) % 60, "");
            if (tmpData != null) {
                ringItemData.addAll(tmpData);
            }
        }
        // ring trigger
        log.debug(">>>>>>>>>>> gls-job, time-ring beat : " + nowSecond + " = " + Collections.singletonList(ringItemData));
        if (ringItemData.size() > 0) {
            // do trigger
            for (Long jobId : ringItemData) {
                // do trigger
                jobAsyncService.asyncTrigger(jobId, TriggerType.CRON, -1, null, null, null);
            }
            // clear
            ringItemData.clear();
        }
    }

    @Override
    public void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList) {
        // load data
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            log.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (StringUtils.hasText(executorParam)) {
            jobInfoEntity.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfoEntity.getExecutorFailRetryCount();

        JobGroupEntity jobGroupEntity = jobInfoEntity.getJobGroup();

        // cover addressList
        if (!ObjectUtils.isEmpty(addressList)) {
            jobGroupEntity.setAddressType(false);
            jobGroupEntity.setAddressList(addressList);
        }
        // sharding param
        int[] shardingParam = null;
        if (StringUtils.hasText(executorShardingParam)) {
            shardingParam = Arrays.stream(executorShardingParam.split("/")).mapToInt(Integer::parseInt).toArray();
        }

        if (ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy())
                && !ObjectUtils.isEmpty(jobGroupEntity.getAddressList())
                && ObjectUtils.isEmpty(shardingParam)) {
            for (int i = 0; i < jobGroupEntity.getAddressList().size(); i++) {
                processTrigger(jobInfoEntity, finalFailRetryCount, triggerType, i, jobGroupEntity.getAddressList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(jobInfoEntity, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }
    }

    @Override
    public boolean doJobSchedule() throws ParseException {
        int preReadCount = (jobAdminProperties.getTriggerPoolFastMax() + jobAdminProperties.getTriggerPoolFastMax()) * 20;
        // 1.pre read
        long nowTime = System.currentTimeMillis();
        List<JobInfoEntity> jobInfoEntities = jobInfoRepository.getScheduleJob(new Date(nowTime + JobConstants.PRE_READ_MS), preReadCount);
        if (ObjectUtils.isEmpty(jobInfoEntities)) {
            return false;
        }

        // 2.push time-ring
        for (JobInfoEntity jobInfoEntity : jobInfoEntities) {
            if (nowTime > jobInfoEntity.getTriggerNextTime().getTime() + JobConstants.PRE_READ_MS) {
                // 2.1、trigger-expire > 5s：pass && make next-trigger-time
                log.warn(">>>>>>>>>>> gls-job, schedule misfire, jobId = " + jobInfoEntity.getId());

                // 1、misfire match
                if (MisfireStrategy.FIRE_ONCE_NOW.equals(jobInfoEntity.getMisfireStrategy())) {
                    // FIRE_ONCE_NOW 》 trigger
                    jobAsyncService.asyncTrigger(jobInfoEntity.getId(), TriggerType.MISFIRE, -1, null, null, null);
                    log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfoEntity.getId());
                }
                // 2、fresh next
                refreshNextValidTime(jobInfoEntity, new Date());
            } else if (nowTime > jobInfoEntity.getTriggerNextTime().getTime()) {
                // 1、trigger
                jobAsyncService.asyncTrigger(jobInfoEntity.getId(), TriggerType.CRON, -1, null, null, null);
                log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfoEntity.getId());

                // 2、fresh next
                refreshNextValidTime(jobInfoEntity, new Date());

                // next-trigger-time in 5s, pre-read again
                if (jobInfoEntity.getTriggerStatus() && nowTime + JobConstants.PRE_READ_MS > jobInfoEntity.getTriggerNextTime().getTime()) {

                    // 1、make ring second
                    int ringSecond = (int) ((jobInfoEntity.getTriggerNextTime().getTime() / 1000) % 60);

                    // 2、push time ring
                    pushTimeRing(ringSecond, jobInfoEntity.getId());

                    // 3、fresh next
                    refreshNextValidTime(jobInfoEntity, jobInfoEntity.getTriggerNextTime());

                }
            } else {
                // 2.3、trigger-pre-read：time-ring trigger && make next-trigger-time

                // 1、make ring second
                int ringSecond = (int) ((jobInfoEntity.getTriggerNextTime().getTime() / 1000) % 60);

                // 2、push time ring
                pushTimeRing(ringSecond, jobInfoEntity.getId());

                // 3、fresh next
                refreshNextValidTime(jobInfoEntity, jobInfoEntity.getTriggerNextTime());

            }
            jobInfoRepository.save(jobInfoEntity);
        }
        return true;
    }

    @Override
    public Map<String, Object> getIndexData(Long jobGroupId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("ExecutorRouteStrategy", ExecutorRouteStrategy.values());
        maps.put("GlueType", GlueType.values());
        maps.put("ExecutorBlockStrategy", ExecutorBlockStrategy.values());
        maps.put("ScheduleType", ScheduleType.values());
        maps.put("MisfireStrategy", MisfireStrategy.values());
        List<JobGroup> allList = jobGroupService.getAllList();
        maps.put("JobGroupList", getJobGroupListByRole(allList));
        maps.put("jobGroup", jobGroupId);
        return maps;
    }

    @Override
    public Map<String, Object> pageList(Long jobGroup, Boolean triggerStatus, String jobDesc, String executorHandler, String author, int start, int length) {
        Page<JobInfoEntity> page = jobInfoRepository.getPage(jobGroup, triggerStatus, jobDesc, executorHandler, author, start, length);
        List<JobInfo> jobInfos = jobInfoConverter.sourceToTargetList(page.getContent());
        Map<String, Object> map = new HashMap<>(3);
        // 总记录数
        map.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        map.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        map.put("data", jobInfos);
        return map;
    }

    @Override
    public void addJobInfo(JobInfo jobInfo) {
        validJobInfoEntity(jobInfo);
        JobInfoEntity jobInfoEntity = jobInfoConverter.targetToSource(jobInfo);
        jobInfoEntity.setGlueUpdateTime(new Date());
        jobInfoRepository.save(jobInfoEntity);
    }

    @Override
    public void updateJobInfo(JobInfo jobInfo) {
        validJobInfoEntity(jobInfo);
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobInfo.getId());
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new JobException("任务ID不存在");
        }
        jobInfoEntity = jobInfoConverter.copyTargetToSource(jobInfo, jobInfoEntity);
        try {
            refreshNextValidTime(jobInfoEntity, new Date(System.currentTimeMillis() + JobConstants.PRE_READ_MS));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JobException("调度类型非法");
        }
        jobInfoRepository.save(jobInfoEntity);
    }

    @Override
    public void removeJobInfo(Long jobInfoId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobInfoId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            return;
        }
        jobInfoRepository.deleteById(jobInfoId);
        jobLogRepository.deleteByJobInfoId(jobInfoId);
        jobLogGlueRepository.deleteByJobInfoId(jobInfoId);
    }

    @Override
    public void stopJobInfo(Long jobInfoId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobInfoId);
        jobInfoEntity.setTriggerStatus(false);
        jobInfoEntity.setTriggerLastTime(null);
        jobInfoEntity.setTriggerNextTime(null);
        jobInfoRepository.save(jobInfoEntity);
    }

    @Override
    public void startJobInfo(Long jobInfoId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobInfoId);
        try {
            refreshNextValidTime(jobInfoEntity, new Date(System.currentTimeMillis() + JobConstants.PRE_READ_MS));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JobException("调度类型非法");
        }
        jobInfoRepository.save(jobInfoEntity);
    }

    @Override
    public List<String> nextTriggerTime(String scheduleType, String scheduleConf) {
        JobInfoEntity jobInfoEntity = new JobInfoEntity();
        jobInfoEntity.setScheduleType(ScheduleType.valueOf(scheduleType));
        jobInfoEntity.setScheduleConf(scheduleConf);

        List<String> result = new ArrayList<>();
        Date lastTime = new Date();
        try {
            for (int i = 0; i < 5; i++) {
                lastTime = generateNextValidTime(jobInfoEntity, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new JobException("调度类型非法");
        }
        return result;
    }

    private void validJobInfoEntity(JobInfo jobInfo) {
        // JobGroup
        if (ObjectUtils.isEmpty(jobInfo.getJobGroup())) {
            throw new JobException("请选择执行器");
        }
        // ScheduleType
        if (jobInfo.getScheduleType() == ScheduleType.CRON) {
            if (ObjectUtils.isEmpty(jobInfo.getScheduleConf()) || CronExpression.isValidExpression(jobInfo.getScheduleConf())) {
                throw new JobException("Cron非法");
            }
        } else if (jobInfo.getScheduleType() == ScheduleType.FIX_RATE) {
            if (ObjectUtils.isEmpty(jobInfo.getScheduleConf())) {
                throw new JobException("调度类型非法");
            }
            try {
                int fixSecond = Integer.parseInt(jobInfo.getScheduleConf());
                if (fixSecond < 1) {
                    throw new JobException("调度类型非法");
                }
            } catch (Exception e) {
                throw new JobException("调度类型非法");
            }
        }
        // GlueType
        if (jobInfo.getGlueType() == GlueType.BEAN) {
            if (ObjectUtils.isEmpty(jobInfo.getExecutorHandler())) {
                throw new JobException("请填写JobHandler");
            }
        } else if (jobInfo.getGlueType() == GlueType.GLUE_SHELL) {
            if (!ObjectUtils.isEmpty(jobInfo.getGlueSource())) {
                jobInfo.setGlueSource(jobInfo.getGlueSource().replaceAll("\r", ""));
            }
        }
    }

    @Override
    public List<JobGroup> getJobGroupListByRole(List<JobGroup> allList) {
        List<JobGroup> jobGroupList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(allList)) {
            JobUser jobUser = LoginUserUtil.getLoginUser();
            if (jobUser.getRole() == 1) {
                jobGroupList = allList;
            } else {
                for (JobGroup jobGroup : allList) {
                    if (LoginUserUtil.validPermission(jobGroup.getId())) {
                        jobGroupList.add(jobGroup);
                    }
                }
            }
        }
        return jobGroupList;
    }

    private void pushTimeRing(int ringSecond, Long id) {
        // push async ring
        List<Long> ringItemData = ringDataHolder.load(ringSecond);
        if (ringItemData == null) {
            ringItemData = new ArrayList<>();
            ringDataHolder.regist(ringSecond, ringItemData, "");
        }
        ringItemData.add(id);

        log.debug(">>>>>>>>>>> gls-job, schedule push time-ring : " + ringSecond + " = " + Collections.singletonList(ringItemData));
    }

    private void refreshNextValidTime(JobInfoEntity jobInfoEntity, Date fromTime) throws ParseException {
        Date nextValidTime = generateNextValidTime(jobInfoEntity, fromTime);
        if (nextValidTime != null) {
            jobInfoEntity.setTriggerLastTime(jobInfoEntity.getTriggerNextTime());
            jobInfoEntity.setTriggerNextTime(nextValidTime);
        } else {
            jobInfoEntity.setTriggerStatus(false);
            jobInfoEntity.setTriggerLastTime(null);
            jobInfoEntity.setTriggerNextTime(null);
            log.warn(">>>>>>>>>>> gls-job, refreshNextValidTime fail for job: jobId={}, scheduleType={}, scheduleConf={}",
                    jobInfoEntity.getId(), jobInfoEntity.getScheduleType(), jobInfoEntity.getScheduleConf());
        }
    }

    private Date generateNextValidTime(JobInfoEntity jobInfoEntity, Date fromTime) throws ParseException {
        ScheduleType scheduleType = jobInfoEntity.getScheduleType();
        if (ScheduleType.CRON == scheduleType) {
            return new CronExpression(jobInfoEntity.getScheduleConf()).getNextValidTimeAfter(fromTime);
        } else if (ScheduleType.FIX_RATE == scheduleType) {
            return new Date(fromTime.getTime() + Integer.parseInt(jobInfoEntity.getScheduleConf()) * 1000L);
        }
        return null;
    }

    private void processTrigger(JobInfoEntity jobInfoEntity, int finalFailRetryCount, TriggerType triggerType, int index, int total) {
        // param
        String shardingParam = ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy()) ? String.valueOf(index).concat("/").concat(String.valueOf(total)) : null;

        // 1、save log-id
        JobLogEntity jobLogEntity = createJobLogEntity(jobInfoEntity);

        // 2、init trigger-param
        TriggerModel triggerModel = createTriggerModel(jobLogEntity, index, total);

        // 3、init address
        String address = getAddress(jobInfoEntity, index);

        // 4、trigger remote executor
        Result<String> result = runExecutor(triggerModel, address);

        // 5、collection trigger info
        String triggerMsg = getTriggerMsg(jobInfoEntity, triggerType, shardingParam, finalFailRetryCount, result);

        // 6、save log trigger-info
        jobLogEntity.setExecutorAddress(address);
        jobLogEntity.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        jobLogEntity.setExecutorParam(jobInfoEntity.getExecutorParam());
        jobLogEntity.setExecutorShardingParam(shardingParam);
        jobLogEntity.setExecutorFailRetryCount(finalFailRetryCount);
        jobLogEntity.setTriggerCode(result.getCode());
        jobLogEntity.setTriggerMsg(triggerMsg);
        jobLogRepository.save(jobLogEntity);
    }

    private String getTriggerMsg(JobInfoEntity jobInfoEntity, TriggerType triggerType, String shardingParam, int finalFailRetryCount, Result<String> result) {
        StringBuilder triggerMsgBuilder = new StringBuilder();
        triggerMsgBuilder.append("任务触发类型：").append(triggerType.getTitle());
        triggerMsgBuilder.append("<br>调度机器：").append(NetUtil.getLocalhostStr());
        triggerMsgBuilder.append("<br>执行器-注册方式：").append(jobInfoEntity.getJobGroup().getAddressType() ? "自动注册" : "手动录入");
        triggerMsgBuilder.append("<br>执行器-地址列表：").append(StringUtil.toString(jobInfoEntity.getJobGroup().getAddressList()));
        triggerMsgBuilder.append("<br>路由策略：").append(jobInfoEntity.getExecutorRouteStrategy().getTitle());
        if (shardingParam != null) {
            triggerMsgBuilder.append("(").append(shardingParam).append(")");
        }
        triggerMsgBuilder.append("<br>阻塞处理策略：").append(jobInfoEntity.getExecutorBlockStrategy().getTitle());
        triggerMsgBuilder.append("<br>任务超时时间：").append(jobInfoEntity.getExecutorTimeout());
        triggerMsgBuilder.append("<br>失败重试次数：").append(finalFailRetryCount);

        triggerMsgBuilder.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>").append(result);
        return triggerMsgBuilder.toString();
    }

    private Result<String> runExecutor(TriggerModel triggerModel, String address) {
        Result<String> result;
        try {
            result = executorApiHolder.load(address).run(triggerModel);
        } catch (Exception e) {
            log.error(">>>>>>>>>>> gls-job trigger error, please check if the executor[{}] is running.", address, e);
            result = new Result<>(Result.FAIL_CODE, Arrays.toString(e.getStackTrace()));
        }
        result.setMessage("触发调度: <br>address：".concat(address)
                .concat("<br>code：").concat(String.valueOf(result.getCode()))
                .concat("<br>msg：").concat(result.getMessage()));
        return result;
    }

    private String getAddress(JobInfoEntity jobInfoEntity, int index) {
        if (ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy())) {
            if (index < jobInfoEntity.getJobGroup().getAddressList().size()) {
                return jobInfoEntity.getJobGroup().getAddressList().get(index);
            } else {
                return jobInfoEntity.getJobGroup().getAddressList().get(0);
            }
        }
        return executorRouterHolder.load(jobInfoEntity.getExecutorRouteStrategy().getRouterKey())
                .route(jobInfoEntity.getId(), jobInfoEntity.getJobGroup().getAddressList());
    }

    private TriggerModel createTriggerModel(JobLogEntity jobLogEntity, int index, int total) {
        JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
        TriggerModel triggerModel = new TriggerModel();
        triggerModel.setJobId(jobInfoEntity.getId());
        triggerModel.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        triggerModel.setExecutorParams(jobInfoEntity.getExecutorParam());
        triggerModel.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        triggerModel.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        triggerModel.setLogId(jobLogEntity.getId());
        triggerModel.setLogDateTime(jobLogEntity.getTriggerTime());
        triggerModel.setGlueType(jobInfoEntity.getGlueType());
        triggerModel.setGlueSource(jobInfoEntity.getGlueSource());
        triggerModel.setGlueUpdateTime(jobInfoEntity.getGlueUpdateTime());
        triggerModel.setBroadcastIndex(index);
        triggerModel.setBroadcastTotal(total);
        return triggerModel;
    }

    private JobLogEntity createJobLogEntity(JobInfoEntity jobInfoEntity) {
        JobLogEntity jobLogEntity = new JobLogEntity();
        jobLogEntity.setJobInfo(jobInfoEntity);
        jobLogEntity.setTriggerTime(new Date());
        jobLogEntity = jobLogRepository.save(jobLogEntity);
        log.debug(">>>>>>>>>>> gls-job trigger start, jobId:{}", jobLogEntity.getId());
        return jobLogEntity;
    }
}
