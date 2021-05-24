package com.gls.job.dashboard.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.job.dashboard.web.repository.JobDetailRepository;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author george
 */
@Component
public class JobDetailConverter extends BaseConverter<JobDetail, JobDetailEntity> {

    @Resource
    private JobDetailRepository jobDetailRepository;

    @Override
    protected JobDetailEntity copySourceToTarget(JobDetail jobDetail) {
        JobDetailEntity entity = jobDetailRepository.findByNameAndGroupName(jobDetail.getKey().getName(), jobDetail.getKey().getGroup());
        entity.setName(jobDetail.getKey().getName());
        entity.setGroupName(jobDetail.getKey().getGroup());
        entity.setDescription(jobDetail.getDescription());
        entity.setDurability(jobDetail.isDurable());
        entity.setShouldRecover(jobDetail.requestsRecovery());
        entity.setJobClassName(jobDetail.getJobClass().getName());
        entity.setJobDataMap(formatString(jobDetail.getJobDataMap()));
        return entity;
    }

    private Map<String, String> formatString(Map<String, Object> map) {
        Map<String, String> result = new HashMap<>(map.size());
        map.forEach((key, value) -> result.put(key, value.toString()));
        return result;
    }

    @Override
    protected JobDetail copyTargetToSource(JobDetailEntity jobDetailEntity) {
        return JobBuilder.newJob(loadClassName(jobDetailEntity.getJobClassName()))
                .withIdentity(jobDetailEntity.getName(), jobDetailEntity.getGroupName())
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
