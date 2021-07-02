package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
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
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.model.query.QueryJobLog;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogReportRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.AsyncService;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.KillModel;
import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
import com.gls.job.core.constants.JobConstants;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author george
 */
@Slf4j
@Service("jobLogService")
public class JobLogServiceImpl extends BaseServiceImpl<JobLogRepository, JobLogConverter, JobLogEntity, JobLog, QueryJobLog> implements JobLogService {
    private static final int HANDLE_MSG_MAX_LENGTH = 15000;
    @Resource
    private AsyncService asyncService;
    @Resource
    private JobGroupService jobGroupService;
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogReportRepository jobLogReportRepository;
    @Resource
    private JobInfoConverter jobInfoConverter;
    @Resource
    private JobAlarmHolder jobAlarmHolder;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private ExecutorApiHolder executorApiHolder;

    public JobLogServiceImpl(JobLogRepository repository, JobLogConverter converter) {
        super(repository, converter);
    }

    @Override
    public void callback(List<CallbackModel> callbackModels) {
        if (ObjectUtils.isEmpty(callbackModels)) {
            return;
        }
        callbackModels.forEach(callbackModel -> {
            JobLogEntity jobLogEntity = repository.getOne(callbackModel.getLogId());
            if (ObjectUtils.isEmpty(jobLogEntity)) {
                throw new GlsException("log item not found.");
            }
            if (jobLogEntity.getHandleCode() > 0) {
                throw new GlsException("log repeat callback.");
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
    public void clearLog(Long groupId, Long jobId, Integer type) {
        Date clearBeforeTime = null;
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
        }
        List<JobLogEntity> jobLogEntityList = repository.findAll(
                getSpec(new QueryJobLog(groupId, jobId, null, clearBeforeTime, null)));
        repository.deleteInBatch(jobLogEntityList);
    }

    @Override
    public void doJobComplete() {
        Date lostTime = DateUtil.offsetMinute(new Date(), -10);
        List<JobLogEntity> jobLogEntities = repository.getLostJobLogs(lostTime);
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
        List<JobLogEntity> jobLogEntities = repository.getFailJobLogs(1000);
        if (ObjectUtils.isEmpty(jobLogEntities)) {
            return;
        }
        jobLogEntities.forEach(jobLogEntity -> {
            // 1、fail retry monitor
            if (jobLogEntity.getExecutorFailRetryCount() > 0) {
                asyncService.asyncTrigger(jobLogEntity.getJobInfo().getId(), TriggerType.RETRY, jobLogEntity.getExecutorFailRetryCount() - 1, jobLogEntity.getExecutorShardingParam(), jobLogEntity.getExecutorParam(), null);
                String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>失败重试触发<<<<<<<<<<< </span><br>";
                jobLogEntity.setTriggerMsg(jobLogEntity.getTriggerMsg() + retryMsg);
            }
            // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
            boolean alarmResult = jobAlarmHolder.alarm(jobLogEntity);
            int newAlarmStatus = alarmResult ? 2 : 3;
            jobLogEntity.setAlarmStatus(newAlarmStatus);
            repository.save(jobLogEntity);
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
        maps.put("jobGroupList", jobGroupService.getByLoginUser());
        // 任务
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new GlsException("任务ID不存在");
        }
        if (!LoginUserUtil.validPermission(jobInfoEntity.getJobGroup().getId())) {
            throw new GlsException("权限拦截");
        }
        maps.put("jobInfo", jobInfoConverter.sourceToTarget(jobInfoEntity));
        return maps;
    }

    @Override
    public LogResultModel logDetailCat(LogModel logModel) {
        JobLogEntity jobLogEntity = repository.getOne(logModel.getLogId());
        LogResultModel logResultModel = executorApiHolder.load(jobLogEntity.getExecutorAddress()).log(logModel).getModel();
        if (!ObjectUtils.isEmpty(logResultModel)
                && logResultModel.getFromLineNum() > logResultModel.getToLineNum()) {
            if (jobLogEntity.getHandleCode() > 0) {
                logResultModel.setIsEnd(true);
            }
        }
        return logResultModel;
    }

    @Override
    public void logKill(Long logId) {
        JobLogEntity jobLogEntity = repository.getOne(logId);
        JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new GlsException("任务ID存在");
        }
        if (!Result.SUCCESS_CODE.equals(jobLogEntity.getTriggerCode())) {
            throw new GlsException("调度失败，无法终止日志");
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
            throw new GlsException(e.getMessage());
        }
    }

    @Override
    protected Specification<JobLogEntity> getSpec(QueryJobLog queryJobLog) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(queryJobLog.getJobGroupId())) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("jobGroup").get("id"), queryJobLog.getJobGroupId()));
            }
            if (!ObjectUtils.isEmpty(queryJobLog.getJobInfoId())) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("id"), queryJobLog.getJobInfoId()));
            }
            if (!ObjectUtils.isEmpty(queryJobLog.getTriggerTimeFrom())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("triggerTime"), queryJobLog.getTriggerTimeFrom()));
            }
            if (!ObjectUtils.isEmpty(queryJobLog.getTriggerTimeTo())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("triggerTime"), queryJobLog.getTriggerTimeTo()));
            }
            if (!ObjectUtils.isEmpty(queryJobLog.getLogStatus())) {
                switch (queryJobLog.getLogStatus()) {
                    case 1:
                        predicates.add(criteriaBuilder.equal(root.get("handleCode"), 200));
                        break;
                    case 2:
                        Predicate predicate1 = criteriaBuilder.not(root.get("triggerCode").in(0, 200));
                        Predicate predicate2 = criteriaBuilder.not(root.get("handleCode").in(0, 200));
                        predicates.add(criteriaBuilder.or(predicate1, predicate2));
                        break;
                    case 3:
                        predicates.add(criteriaBuilder.equal(root.get("triggerCode"), 200));
                        predicates.add(criteriaBuilder.equal(root.get("handleCode"), 0));
                        break;
                    default:
                        log.info("logStatus : {}", queryJobLog.getLogStatus());
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
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
            getPage(
                    new QueryJobLog(null, null, clearBeforeTime, null, null),
                    PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "triggerTime")))
                    .get().map(JobLog::getId).forEach(repository::deleteById);
            // update clean time
            lastCleanLogTime = System.currentTimeMillis();
        }
        return lastCleanLogTime;
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
                        asyncService.asyncTrigger(jobLogEntity.getJobInfo().getId(), TriggerType.RETRY, jobLogEntity.getExecutorFailRetryCount() - 1, jobLogEntity.getExecutorShardingParam(), jobLogEntity.getExecutorParam(), null);
                        triggerChildMsgBuilder.append(i).append("/").append(childJobInfoEntities.size())
                                .append(" [任务ID=").append(childJobInfoEntity.getId()).append("], 触发成功. <br>");
                    }
                }
            }
        }
        jobLogEntity.setHandleMsg(jobLogEntity.getHandleMsg().concat(triggerChildMsgBuilder.toString()));
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
        Map<String, Long> result = repository.getLogReport(todayFrom, todayTo);
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
        repository.save(jobLogEntity);
    }
}
