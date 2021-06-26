package com.gls.job.admin.web.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.JsonUtil;
import com.gls.job.admin.constants.JobAdminProperties;
import com.gls.job.admin.constants.TriggerType;
import com.gls.job.admin.core.alarm.JobAlarmHolder;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.converter.JobLogConverter;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogReportRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobAsyncService;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.KillModel;
import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author george
 */
@Slf4j
@Service("jobLogService")
public class JobLogServiceImpl implements JobLogService {

    private static final int HANDLE_MSG_MAX_LENGTH = 15000;
    @Resource
    private JobAsyncService jobAsyncService;
    @Resource
    private JobGroupService jobGroupService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogReportRepository jobLogReportRepository;
    @Resource
    private JobInfoConverter jobInfoConverter;
    @Resource
    private JobLogConverter jobLogConverter;
    @Resource
    private JobAlarmHolder jobAlarmHolder;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private ExecutorApiHolder executorApiHolder;

    @Override
    public void callback(List<CallbackModel> callbackModels) {
        if (ObjectUtils.isEmpty(callbackModels)) {
            return;
        }
        callbackModels.forEach(callbackModel -> {
            JobLogEntity jobLogEntity = jobLogRepository.getOne(callbackModel.getLogId());
            if (ObjectUtils.isEmpty(jobLogEntity)) {
                throw new JobException("log item not found.");
            }
            if (jobLogEntity.getHandleCode() > 0) {
                throw new JobException("log repeat callback.");
            }

            StringBuilder handleMsgBuilder = new StringBuilder();
            if (StringUtils.hasText(jobLogEntity.getHandleMsg())) {
                handleMsgBuilder.append(jobLogEntity.getHandleMsg()).append("<br>");
            }
            if (StringUtils.hasText(callbackModel.getHandleMsg())) {
                handleMsgBuilder.append(callbackModel.getHandleMsg());
            }

            jobLogEntity.setHandleCode(callbackModel.getHandleCode());
            jobLogEntity.setHandleMsg(handleMsgBuilder.toString());
            jobLogEntity.setHandleTime(new Date());
            updateHandleInfoAndFinish(jobLogEntity);
        });
    }

    @Override
    public void doJobComplete() {
        Date lostTime = DateUtil.offsetMinute(new Date(), -10);
        List<JobLogEntity> jobLogEntities = jobLogRepository.getLostJobLogs(lostTime);
        if (ObjectUtils.isEmpty(jobLogEntities)) {
            return;
        }
        jobLogEntities.forEach(jobLogEntity -> {
            jobLogEntity.setHandleCode(Result.FAIL_CODE);
            jobLogEntity.setHandleMsg("任务结果丢失，标记失败");
            jobLogEntity.setHandleTime(new Date());
            updateHandleInfoAndFinish(jobLogEntity);
        });
    }

    @Override
    public void doJobFail() {
        List<JobLogEntity> jobLogEntities = jobLogRepository.getFailJobLogs(1000);
        if (ObjectUtils.isEmpty(jobLogEntities)) {
            return;
        }
        jobLogEntities.forEach(jobLogEntity -> {
            // 1、fail retry monitor
            if (jobLogEntity.getExecutorFailRetryCount() > 0) {
                jobAsyncService.asyncTrigger(jobLogEntity.getJobInfo().getId(), TriggerType.RETRY, jobLogEntity.getExecutorFailRetryCount() - 1, jobLogEntity.getExecutorShardingParam(), jobLogEntity.getExecutorParam(), null);
                String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>";
                jobLogEntity.setTriggerMsg(jobLogEntity.getTriggerMsg() + retryMsg);
            }

            // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
            boolean alarmResult = jobAlarmHolder.alarm(jobLogEntity);
            int newAlarmStatus = alarmResult ? 2 : 3;
            jobLogEntity.setAlarmStatus(newAlarmStatus);
            jobLogRepository.save(jobLogEntity);
        });
    }

    @Override
    public long doJobLogReport(long lastCleanLogTime) {
        //refresh JobLogReport
        for (int i = 0; i < 3; i++) {
            refreshJobLogReport(i);
        }
        return cleanJobLog(lastCleanLogTime);
    }

    @Override
    public Map<String, Object> getIndexMap(Long jobId) {
        Map<String, Object> maps = new HashMap<>();
        // 执行器列表
        List<JobGroup> jobGroupList = jobGroupService.getAllList();
        maps.put("jobGroupList", jobInfoService.getJobGroupListByRole(jobGroupList));

        // 任务
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new JobException("任务ID不存在");
        }
        if (!LoginUserUtil.validPermission(jobInfoEntity.getJobGroup().getId())) {
            throw new JobException("权限拦截");
        }
        maps.put("jobInfo", jobInfoConverter.sourceToTarget(jobInfoEntity));
        return maps;
    }

    @Override
    public List<JobInfo> getJobsByGroup(Long jobGroupId) {
        return jobInfoConverter.sourceToTargetList(jobInfoRepository.getByJobGroupId(jobGroupId));
    }

    @Override
    public Map<String, Object> pageList(Long jobGroup, Long jobId, int logStatus, String filterTime, int start, int length) {
        if (!LoginUserUtil.validPermission(jobGroup)) {
            throw new JobException("权限拦截");
        }

        // parse param
        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (filterTime != null && filterTime.trim().length() > 0) {
            String[] temp = filterTime.split(" - ");
            if (temp.length == 2) {
                triggerTimeStart = DateUtil.parseDateTime(temp[0]);
                triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
            }
        }
        Page<JobLogEntity> page = jobLogRepository.getPage(jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus, start, length);

        // package result
        Map<String, Object> maps = new HashMap<>();
        // 总记录数
        maps.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        maps.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        maps.put("data", jobLogConverter.sourceToTargetList(page.getContent()));
        return maps;
    }

    @Override
    public JobLog logDetail(Long logId) {
        return jobLogConverter.sourceToTarget(jobLogRepository.getOne(logId));
    }

    @Override
    public LogResultModel logDetailCat(String executorAddress, Long triggerTime, Long logId, Integer fromLineNum) {
        Result<LogResultModel> logResult = executorApiHolder.load(executorAddress).log(new LogModel(Convert.toDate(triggerTime), logId, fromLineNum));

        if (logResult.getModel() != null && logResult.getModel().getFromLineNum() > logResult.getModel().getToLineNum()) {
            JobLogEntity jobLogEntity = jobLogRepository.getOne(logId);
            if (jobLogEntity.getHandleCode() > 0) {
                logResult.getModel().setIsEnd(true);
            }
        }
        return logResult.getModel();
    }

    @Override
    public void logKill(Long logId) {
        JobLogEntity jobLogEntity = jobLogRepository.getOne(logId);
        JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new JobException("任务ID存在");
        }
        if (!Result.SUCCESS_CODE.equals(jobLogEntity.getTriggerCode())) {
            throw new JobException("调度失败，无法终止日志");
        }
        try {
            Result<String> result = executorApiHolder.load(jobLogEntity.getExecutorAddress()).kill(new KillModel(logId));
            if (Result.SUCCESS_CODE.equals(result.getCode())) {
                jobLogEntity.setHandleCode(Result.FAIL_CODE);
                jobLogEntity.setHandleMsg("人为操作，主动终止:" + result.getMessage());
                jobLogEntity.setHandleTime(new Date());
                updateHandleInfoAndFinish(jobLogEntity);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JobException(e.getMessage());
        }
    }

    @Override
    public void clearLog(Long groupId, Long jobId, Integer type) {
        Date clearBeforeTime = null;
        int start = 0;
        if (type == 1) {
            // 清理一个月之前日志数据
            clearBeforeTime = DateUtil.offsetMonth(new Date(), -1);
        } else if (type == 2) {
            // 清理三个月之前日志数据
            clearBeforeTime = DateUtil.offsetMonth(new Date(), -3);
        } else if (type == 3) {
            // 清理六个月之前日志数据
            clearBeforeTime = DateUtil.offsetMonth(new Date(), -6);
        } else if (type == 4) {
            // 清理一年之前日志数据
            clearBeforeTime = DateUtil.offsetMonth(new Date(), -12);
        } else if (type == 5) {
            // 清理一千条以前日志数据
            start = 1000;
        } else if (type == 6) {
            // 清理一万条以前日志数据
            start = 10000;
        } else if (type == 7) {
            // 清理三万条以前日志数据
            start = 30000;
        } else if (type == 8) {
            // 清理十万条以前日志数据
            start = 100000;
        } else if (type == 9) {
            // 清理所有日志数据
            start = 0;
        } else {
            throw new JobException("清理类型参数异常");
        }
        List<JobLogEntity> jobLogEntities = jobLogRepository.getPage(groupId, jobId, null, clearBeforeTime, null, 2, start).getContent();
        jobLogRepository.deleteAll(jobLogEntities);
    }

    private long cleanJobLog(long lastCleanLogTime) {
        // 2、log-clean: switch open & once each day
        if (jobAdminProperties.getLogRetentionDays() > 0 && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

            // expire-time
            Calendar expiredDay = Calendar.getInstance();
            expiredDay.add(Calendar.DAY_OF_MONTH, -1 * jobAdminProperties.getLogRetentionDays());
            expiredDay.set(Calendar.HOUR_OF_DAY, 0);
            expiredDay.set(Calendar.MINUTE, 0);
            expiredDay.set(Calendar.SECOND, 0);
            expiredDay.set(Calendar.MILLISECOND, 0);
            Date clearBeforeTime = expiredDay.getTime();

            // clean expired log

            List<JobLogEntity> jobLogEntities = jobLogRepository.getPage(null, null, clearBeforeTime, null, null, 0, 1000).getContent();
            jobLogRepository.deleteAll(jobLogEntities);

            // update clean time
            lastCleanLogTime = System.currentTimeMillis();
        }
        return lastCleanLogTime;
    }

    private void refreshJobLogReport(int i) {
        // today
        Calendar itemDay = Calendar.getInstance();
        itemDay.add(Calendar.DAY_OF_MONTH, -i);
        itemDay.set(Calendar.HOUR_OF_DAY, 0);
        itemDay.set(Calendar.MINUTE, 0);
        itemDay.set(Calendar.SECOND, 0);
        itemDay.set(Calendar.MILLISECOND, 0);

        Date todayFrom = itemDay.getTime();

        itemDay.set(Calendar.HOUR_OF_DAY, 23);
        itemDay.set(Calendar.MINUTE, 59);
        itemDay.set(Calendar.SECOND, 59);
        itemDay.set(Calendar.MILLISECOND, 999);

        Date todayTo = itemDay.getTime();

        // refresh log-report every minute

        JobLogReportEntity jobLogReportEntity = new JobLogReportEntity();
        jobLogReportEntity.setTriggerDay(todayFrom);
        Optional<JobLogReportEntity> optional = jobLogReportRepository.getByTriggerDay(todayFrom);
        if (optional.isPresent()) {
            jobLogReportEntity = optional.get();
        }

        Map<String, Long> result = jobLogRepository.getLogReport(todayFrom, todayTo);
        log.info("result: {}", JsonUtil.writeValueAsString(result));
        if (!ObjectUtils.isEmpty(result)) {
            Long triggerDayCount = result.get("triggerDayCount");
            Long triggerDayCountRunning = result.get("triggerDayCountRunning");
            Long triggerDayCountSuc = result.get("triggerDayCountSuc");
            if (ObjectUtils.isEmpty(triggerDayCount)) {
                triggerDayCount = 0L;
            }
            if (ObjectUtils.isEmpty(triggerDayCountRunning)) {
                triggerDayCountRunning = 0L;
            }
            if (ObjectUtils.isEmpty(triggerDayCountSuc)) {
                triggerDayCountSuc = 0L;
            }
            Long triggerDayCountFail = triggerDayCount - triggerDayCountRunning - triggerDayCountSuc;

            jobLogReportEntity.setRunningCount(triggerDayCountRunning);
            jobLogReportEntity.setSucCount(triggerDayCountSuc);
            jobLogReportEntity.setFailCount(triggerDayCountFail);
        }
        // do refresh
        jobLogReportRepository.save(jobLogReportEntity);
    }

    private void updateHandleInfoAndFinish(JobLogEntity jobLogEntity) {
        finishJob(jobLogEntity);
        if (jobLogEntity.getHandleMsg().length() > HANDLE_MSG_MAX_LENGTH) {
            jobLogEntity.setHandleMsg(jobLogEntity.getHandleMsg().substring(0, HANDLE_MSG_MAX_LENGTH));
        }
        jobLogRepository.save(jobLogEntity);
    }

    private void finishJob(JobLogEntity jobLogEntity) {
        // JobInfoEntity
        StringBuilder triggerChildMsgBuilder = new StringBuilder();
        if (JobConstants.HANDLE_CODE_SUCCESS == jobLogEntity.getHandleCode()) {
            JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
            if (!ObjectUtils.isEmpty(jobInfoEntity)) {
                List<JobInfoEntity> childJobInfoEntities = jobInfoEntity.getChildJobs();
                if (!ObjectUtils.isEmpty(childJobInfoEntities)) {
                    for (int i = 0; i < childJobInfoEntities.size(); i++) {
                        JobInfoEntity childJobInfoEntity = childJobInfoEntities.get(i);
                        jobAsyncService.asyncTrigger(jobLogEntity.getJobInfo().getId(), TriggerType.RETRY, jobLogEntity.getExecutorFailRetryCount() - 1, jobLogEntity.getExecutorShardingParam(), jobLogEntity.getExecutorParam(), null);
                        triggerChildMsgBuilder.append(i).append("/").append(childJobInfoEntities.size())
                                .append(" [任务ID=").append(childJobInfoEntity.getId()).append("], 触发成功. <br>");
                    }
                }
            }
        }

        jobLogEntity.setHandleMsg(jobLogEntity.getHandleMsg().concat(triggerChildMsgBuilder.toString()));
    }
}
