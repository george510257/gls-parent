package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.model.JobRegistry;
import com.gls.job.core.constants.RegistryType;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author george
 */
@Component
public class JobRegistryConverter implements BaseConverter<JobRegistryEntity, JobRegistry> {
    @Override
    public JobRegistry copySourceToTarget(JobRegistryEntity jobRegistryEntity, JobRegistry jobRegistry) {
        jobRegistry.setId(jobRegistryEntity.getId());
        jobRegistry.setRegistryGroup(jobRegistryEntity.getRegistryGroup().name());
        jobRegistry.setRegistryKey(jobRegistryEntity.getRegistryKey());
        jobRegistry.setRegistryValue(jobRegistryEntity.getRegistryValue());
        jobRegistry.setUpdateTime(jobRegistryEntity.getUpdateDate());
        return jobRegistry;
    }

    @Override
    public JobRegistryEntity copyTargetToSource(JobRegistry jobRegistry, JobRegistryEntity jobRegistryEntity) {
        jobRegistryEntity.setRegistryGroup(RegistryType.valueOf(jobRegistry.getRegistryGroup()));
        jobRegistryEntity.setRegistryKey(jobRegistry.getRegistryKey());
        jobRegistryEntity.setRegistryValue(jobRegistry.getRegistryValue());
        jobRegistryEntity.setId(jobRegistry.getId());
        jobRegistryEntity.setUpdateDate(new Timestamp(jobRegistry.getUpdateTime().getTime()));
        return jobRegistryEntity;
    }
}
