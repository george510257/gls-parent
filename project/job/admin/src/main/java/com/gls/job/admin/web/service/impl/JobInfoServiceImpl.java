package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.constants.ExecutorRouteStrategy;
import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.core.route.ExecutorRouterHolder;
import com.gls.job.admin.core.support.JobScheduleHelper;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
import com.gls.job.core.constants.JobConstants;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service("jobInfoService")
public class JobInfoServiceImpl extends BaseServiceImpl<JobInfoRepository, JobInfoConverter, JobInfoEntity, JobInfo, QueryJobInfo> implements JobInfoService {
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;
    @Resource
    private ExecutorRouterHolder executorRouterHolder;
    @Resource
    private ExecutorApiHolder executorApiHolder;

    public JobInfoServiceImpl(JobInfoRepository repository, JobInfoConverter converter) {
        super(repository, converter);
    }

    @Override
    public void update(JobInfo model) {
        JobScheduleHelper.refreshNextValidTime(model, new Date(System.currentTimeMillis() + JobConstants.PRE_READ_MS));
        super.update(model);
    }

    @Override
    public void remove(Long id) {
        super.remove(id);
        jobLogRepository.deleteByJobInfoId(id);
        jobLogGlueRepository.deleteByJobInfoId(id);
    }

    @Override
    protected Specification<JobInfoEntity> getSpec(QueryJobInfo queryJobInfo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(queryJobInfo.getJobGroupId())) {
                predicates.add(criteriaBuilder.equal(root.get("jobGroup").get("id"), queryJobInfo.getJobGroupId()));
            }
            if (!ObjectUtils.isEmpty(queryJobInfo.getTriggerStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("triggerStatus"), queryJobInfo.getTriggerStatus()));
            }
            if (StringUtils.hasText(queryJobInfo.getJobDesc())) {
                predicates.add(criteriaBuilder.like(root.get("jobDesc"), "%" + queryJobInfo.getJobDesc() + "%"));
            }
            if (StringUtils.hasText(queryJobInfo.getExecutorHandler())) {
                predicates.add(criteriaBuilder.like(root.get("executorHandler"), "%" + queryJobInfo.getExecutorHandler() + "%"));
            }
            if (StringUtils.hasText(queryJobInfo.getAuthor())) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + queryJobInfo.getAuthor() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[5]));
        };
    }

    @Override
    public void start(Long jobInfoId) {
        JobInfo jobInfo = getById(jobInfoId);
        update(jobInfo);
    }

    @Override
    public void stop(Long jobInfoId) {
        JobInfoEntity jobInfoEntity = repository.getOne(jobInfoId);
        jobInfoEntity.setTriggerStatus(false);
        jobInfoEntity.setTriggerLastTime(null);
        jobInfoEntity.setTriggerNextTime(null);
        repository.save(jobInfoEntity);
    }

    @Override
    public List<JobInfo> getByJobGroupId(Long jobGroupId) {
        return converter.sourceToTargetList(repository.getByJobGroupId(jobGroupId));
    }

    @Override
    public void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList) {
        // load data
        JobInfoEntity jobInfoEntity = repository.getOne(jobId);
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
    public List<String> nextTriggerTime(String scheduleType, String scheduleConf) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setScheduleType(ScheduleType.valueOf(scheduleType));
        jobInfo.setScheduleConf(scheduleConf);
        List<String> result = new ArrayList<>();
        Date lastTime = new Date();
        for (int i = 0; i < 5; i++) {
            lastTime = JobScheduleHelper.generateNextValidTime(jobInfo, lastTime);
            if (lastTime != null) {
                result.add(DateUtil.formatDateTime(lastTime));
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public List<JobInfo> getScheduleJob(Date date, int preReadCount) {
        return converter.sourceToTargetList(repository.getScheduleJob(date, preReadCount));
    }

    private JobLogEntity createJobLogEntity(JobInfoEntity jobInfoEntity) {
        JobLogEntity jobLogEntity = new JobLogEntity();
        jobLogEntity.setJobInfo(jobInfoEntity);
        jobLogEntity.setTriggerTime(new Date());
        jobLogEntity = jobLogRepository.save(jobLogEntity);
        log.debug(">>>>>>>>>>> gls-job trigger start, jobId:{}", jobLogEntity.getId());
        return jobLogEntity;
    }

    private TriggerModel createTriggerModel(JobLogEntity jobLogEntity, int index, int total) {
        JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
        JobLogGlueEntity jobLogGlueEntity = jobLogGlueRepository.getTopByJobInfoIdOrderByUpdateDateDesc(jobInfoEntity.getId());
        TriggerModel triggerModel = new TriggerModel();
        triggerModel.setJobId(jobInfoEntity.getId());
        triggerModel.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        triggerModel.setExecutorParams(jobInfoEntity.getExecutorParam());
        triggerModel.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        triggerModel.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        triggerModel.setLogId(jobLogEntity.getId());
        triggerModel.setLogDateTime(jobLogEntity.getTriggerTime());
        triggerModel.setGlueType(jobLogGlueEntity.getGlueType());
        triggerModel.setGlueSource(jobLogGlueEntity.getGlueSource());
        triggerModel.setGlueUpdateTime(jobLogGlueEntity.getUpdateDate());
        triggerModel.setBroadcastIndex(index);
        triggerModel.setBroadcastTotal(total);
        return triggerModel;
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
}
