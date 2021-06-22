package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.impl.JobDetailImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Component
public class JobDetailConverter implements BaseConverter<JobDetailEntity, JobDetailImpl> {

    @Override
    public JobDetailImpl copySourceToTarget(JobDetailEntity jobDetailEntity, JobDetailImpl jobDetail) {
        jobDetail.setKey(new JobKey(jobDetailEntity.getName(), jobDetailEntity.getGroupName()));
        jobDetail.setDescription(jobDetailEntity.getDescription());
        jobDetail.setJobClass(getJobClass(jobDetailEntity.getJobClassName()));
        jobDetail.setJobDataMap(new JobDataMap(jobDetailEntity.getJobDataMap()));
        jobDetail.setDurability(jobDetailEntity.getDurability());
        jobDetail.setRequestsRecovery(jobDetailEntity.getShouldRecover());
        return jobDetail;
    }

    @Override
    public JobDetailEntity copyTargetToSource(JobDetailImpl jobDetail, JobDetailEntity jobDetailEntity) {
        jobDetailEntity.setGroupName(jobDetail.getKey().getGroup());
        jobDetailEntity.setDescription(jobDetail.getDescription());
        jobDetailEntity.setJobClassName(jobDetail.getJobClass().getName());
        jobDetailEntity.setJobDataMap(getJobDataMap(jobDetail.getJobDataMap()));
        jobDetailEntity.setDurability(jobDetail.isDurable());
        jobDetailEntity.setShouldRecover(jobDetail.requestsRecovery());
        jobDetailEntity.setName(jobDetail.getKey().getName());
        return jobDetailEntity;
    }

    private Map<String, String> getJobDataMap(JobDataMap jobDataMap) {
        return jobDataMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));

    }

    private Class<? extends Job> getJobClass(String jobClassName) {
        try {
            return (Class<? extends Job>) Class.forName(jobClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
