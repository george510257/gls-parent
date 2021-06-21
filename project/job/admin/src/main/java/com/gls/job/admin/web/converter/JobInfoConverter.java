package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.utils.StringUtils;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobInfoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Component
public class JobInfoConverter implements BaseConverter<JobInfoEntity, JobInfo> {

    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public JobInfo copySourceToTarget(JobInfoEntity jobInfoEntity, JobInfo jobInfo) {
        jobInfo.setId(jobInfoEntity.getId());
        jobInfo.setJobGroup(jobInfoEntity.getJobGroup().getId());
        jobInfo.setJobDesc(jobInfoEntity.getJobDesc());
        jobInfo.setAddTime(jobInfoEntity.getCreatedDate());
        jobInfo.setUpdateTime(jobInfoEntity.getUpdateDate());
        jobInfo.setAuthor(jobInfoEntity.getAuthor());
        jobInfo.setAlarmEmail(jobInfoEntity.getAlarmEmail());
        jobInfo.setScheduleType(jobInfoEntity.getScheduleType());
        jobInfo.setScheduleConf(jobInfoEntity.getScheduleConf());
        jobInfo.setMisfireStrategy(jobInfoEntity.getMisfireStrategy());
        jobInfo.setExecutorRouteStrategy(jobInfoEntity.getExecutorRouteStrategy());
        jobInfo.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        jobInfo.setExecutorParam(jobInfoEntity.getExecutorParam());
        jobInfo.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        jobInfo.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        jobInfo.setExecutorFailRetryCount(jobInfoEntity.getExecutorFailRetryCount());
        jobInfo.setGlueType(jobInfoEntity.getGlueType());
        jobInfo.setGlueSource(jobInfoEntity.getGlueSource());
        jobInfo.setGlueRemark(jobInfoEntity.getGlueRemark());
        jobInfo.setGlueUpdateTime(jobInfoEntity.getGlueUpdateTime());
        jobInfo.setChildJobId(StringUtils.toString(jobInfoEntity.getChildJobs().stream().map(child -> child.getId().toString()).collect(Collectors.toList())));
        jobInfo.setTriggerStatus(jobInfoEntity.getTriggerStatus() ? 1 : 0);
        jobInfo.setTriggerLastTime(jobInfoEntity.getTriggerLastTime().getTime());
        jobInfo.setTriggerNextTime(jobInfoEntity.getTriggerNextTime().getTime());
        return jobInfo;
    }

    @Override
    public JobInfoEntity copyTargetToSource(JobInfo jobInfo, JobInfoEntity jobInfoEntity) {
        jobInfoEntity.setJobGroup(jobGroupRepository.getOne(jobInfo.getJobGroup()));
        jobInfoEntity.setJobDesc(jobInfo.getJobDesc());
        jobInfoEntity.setAuthor(jobInfo.getAuthor());
        jobInfoEntity.setAlarmEmail(jobInfo.getAlarmEmail());
        jobInfoEntity.setScheduleType(jobInfo.getScheduleType());
        jobInfoEntity.setScheduleConf(jobInfo.getScheduleConf());
        jobInfoEntity.setMisfireStrategy(jobInfo.getMisfireStrategy());
        jobInfoEntity.setExecutorRouteStrategy(jobInfo.getExecutorRouteStrategy());
        jobInfoEntity.setExecutorHandler(jobInfo.getExecutorHandler());
        jobInfoEntity.setExecutorParam(jobInfo.getExecutorParam());
        jobInfoEntity.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
        jobInfoEntity.setExecutorTimeout(jobInfo.getExecutorTimeout());
        jobInfoEntity.setExecutorFailRetryCount(jobInfo.getExecutorFailRetryCount());
        jobInfoEntity.setGlueType(jobInfo.getGlueType());
        jobInfoEntity.setGlueSource(jobInfo.getGlueSource());
        jobInfoEntity.setGlueRemark(jobInfo.getGlueRemark());
        jobInfoEntity.setGlueUpdateTime(jobInfo.getGlueUpdateTime());
        jobInfoEntity.setChildJobs(StringUtils.toList(jobInfo.getChildJobId()).stream().map(jobId -> jobInfoRepository.getOne(Long.parseLong(jobId))).collect(Collectors.toList()));
        jobInfoEntity.setTriggerStatus(jobInfo.getTriggerStatus() == 1);
        jobInfoEntity.setTriggerLastTime(new Date(jobInfo.getTriggerLastTime()));
        jobInfoEntity.setTriggerNextTime(new Date(jobInfo.getTriggerNextTime()));
        jobInfoEntity.setId(jobInfo.getId());
        jobInfoEntity.setCreatedDate(new Timestamp(jobInfo.getAddTime().getTime()));
        jobInfoEntity.setUpdateDate(new Timestamp(jobInfo.getUpdateTime().getTime()));
        return jobInfoEntity;
    }
}
