package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.model.JobLogGlue;
import com.gls.job.admin.web.repository.JobInfoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author george
 */
@Component
public class JobLogGlueConverter implements BaseConverter<JobLogGlueEntity, JobLogGlue> {

    @Resource
    private JobInfoRepository jobInfoRepository;

    @Override
    public JobLogGlue copySourceToTarget(JobLogGlueEntity jobLogGlueEntity, JobLogGlue jobLogGlue) {
        jobLogGlue.setId(jobLogGlueEntity.getId());
        jobLogGlue.setJobId(jobLogGlueEntity.getJobInfo().getId());
        jobLogGlue.setGlueType(jobLogGlueEntity.getGlueType());
        jobLogGlue.setGlueSource(jobLogGlueEntity.getGlueSource());
        jobLogGlue.setGlueRemark(jobLogGlueEntity.getGlueRemark());
        jobLogGlue.setAddTime(jobLogGlueEntity.getCreatedDate());
        jobLogGlue.setUpdateTime(jobLogGlueEntity.getUpdateDate());
        return jobLogGlue;
    }

    @Override
    public JobLogGlueEntity copyTargetToSource(JobLogGlue jobLogGlue, JobLogGlueEntity jobLogGlueEntity) {
        jobLogGlueEntity.setJobInfo(jobInfoRepository.getOne(jobLogGlue.getJobId()));
        jobLogGlueEntity.setGlueType(jobLogGlue.getGlueType());
        jobLogGlueEntity.setGlueSource(jobLogGlue.getGlueSource());
        jobLogGlueEntity.setGlueRemark(jobLogGlue.getGlueRemark());
        jobLogGlueEntity.setId(jobLogGlue.getId());
        jobLogGlueEntity.setCreatedDate(new Timestamp(jobLogGlue.getAddTime().getTime()));
        jobLogGlueEntity.setUpdateDate(new Timestamp(jobLogGlue.getUpdateTime().getTime()));
        return jobLogGlueEntity;
    }
}
