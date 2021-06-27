package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.repository.JobInfoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class JobLogConverter implements BaseConverter<JobLogEntity, JobLog> {
    @Resource
    private JobInfoRepository jobInfoRepository;

    @Override
    public JobLog copySourceToTarget(JobLogEntity jobLogEntity, JobLog jobLog) {
        jobLog.setId(jobLogEntity.getId());
        jobLog.setJobGroup(jobLogEntity.getJobInfo().getJobGroup().getId());
        jobLog.setJobId(jobLogEntity.getJobInfo().getId());
        jobLog.setExecutorAddress(jobLogEntity.getExecutorAddress());
        jobLog.setExecutorHandler(jobLogEntity.getExecutorHandler());
        jobLog.setExecutorParam(jobLogEntity.getExecutorParam());
        jobLog.setExecutorShardingParam(jobLogEntity.getExecutorShardingParam());
        jobLog.setExecutorFailRetryCount(jobLogEntity.getExecutorFailRetryCount());
        jobLog.setTriggerTime(jobLogEntity.getTriggerTime());
        jobLog.setTriggerCode(jobLogEntity.getTriggerCode());
        jobLog.setTriggerMsg(jobLogEntity.getTriggerMsg());
        jobLog.setHandleTime(jobLogEntity.getHandleTime());
        jobLog.setHandleCode(jobLogEntity.getHandleCode());
        jobLog.setHandleMsg(jobLogEntity.getHandleMsg());
        jobLog.setAlarmStatus(jobLogEntity.getAlarmStatus());
        return jobLog;
    }

    @Override
    public JobLogEntity copyTargetToSource(JobLog jobLog, JobLogEntity jobLogEntity) {
        jobLogEntity.setJobInfo(jobInfoRepository.getOne(jobLog.getJobId()));
        jobLogEntity.setExecutorAddress(jobLog.getExecutorAddress());
        jobLogEntity.setExecutorHandler(jobLog.getExecutorHandler());
        jobLogEntity.setExecutorParam(jobLog.getExecutorParam());
        jobLogEntity.setExecutorShardingParam(jobLog.getExecutorShardingParam());
        jobLogEntity.setExecutorFailRetryCount(jobLog.getExecutorFailRetryCount());
        jobLogEntity.setTriggerTime(jobLog.getTriggerTime());
        jobLogEntity.setTriggerCode(jobLog.getTriggerCode());
        jobLogEntity.setTriggerMsg(jobLog.getTriggerMsg());
        jobLogEntity.setHandleTime(jobLog.getHandleTime());
        jobLogEntity.setHandleCode(jobLog.getHandleCode());
        jobLogEntity.setHandleMsg(jobLog.getHandleMsg());
        jobLogEntity.setAlarmStatus(jobLog.getAlarmStatus());
        jobLogEntity.setId(jobLog.getId());
        return jobLogEntity;
    }
}
