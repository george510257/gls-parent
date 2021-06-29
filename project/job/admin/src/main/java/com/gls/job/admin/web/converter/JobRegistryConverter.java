package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.core.api.model.RegistryModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class JobRegistryConverter implements BaseConverter<JobRegistryEntity, RegistryModel> {
    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public RegistryModel copySourceToTarget(JobRegistryEntity jobRegistryEntity, RegistryModel registryModel) {
        registryModel.setId(jobRegistryEntity.getId());
        registryModel.setRegistryGroup(jobRegistryEntity.getRegistryGroup());
        registryModel.setRegistryKey(jobRegistryEntity.getRegistryKey());
        registryModel.setRegistryValue(jobRegistryEntity.getRegistryValue());
        return registryModel;
    }

    @Override
    public JobRegistryEntity copyTargetToSource(RegistryModel registryModel, JobRegistryEntity jobRegistryEntity) {
        jobRegistryEntity.setJobGroup(jobGroupRepository.getByAppname(registryModel.getRegistryKey()));
        jobRegistryEntity.setRegistryGroup(registryModel.getRegistryGroup());
        jobRegistryEntity.setRegistryKey(registryModel.getRegistryKey());
        jobRegistryEntity.setRegistryValue(registryModel.getRegistryValue());
        jobRegistryEntity.setId(registryModel.getId());
        return jobRegistryEntity;
    }
}
