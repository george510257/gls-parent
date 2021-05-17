package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class JobDetailConverter extends BaseConverter<JobDetail, JobDetailEntity> {

    @Override
    protected JobDetailEntity copySourceToTarget(JobDetail jobDetail) {
        JobDetailEntity entity = new JobDetailEntity();
        entity.setName(jobDetail.getKey().getName());
        entity.setGroup(jobDetail.getKey().getGroup());
        entity.setDescription(jobDetail.getDescription());
        entity.setDurability(jobDetail.isDurable());
        entity.setShouldRecover(jobDetail.requestsRecovery());
        entity.setJobClassName(jobDetail.getJobClass().getName());
        entity.setJobDataMap(jobDetail.getJobDataMap());
        return entity;
    }

    @Override
    protected JobDetail copyTargetToSource(JobDetailEntity jobDetailEntity) {
        return JobBuilder.newJob(loadClassName(jobDetailEntity.getJobClassName()))
                .withIdentity(jobDetailEntity.getName(), jobDetailEntity.getGroup())
                .withDescription(jobDetailEntity.getDescription())
                .storeDurably(jobDetailEntity.getDurability())
                .requestRecovery(jobDetailEntity.getShouldRecover())
                .setJobData(new JobDataMap(jobDetailEntity.getJobDataMap()))
                .build();
    }

    private Class<? extends Job> loadClassName(String jobClassName) {
        try {
            return (Class<? extends Job>) Class.forName(jobClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
