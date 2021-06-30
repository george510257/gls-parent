package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.repository.JobRegistryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Component
public class JobGroupConverter implements BaseConverter<JobGroupEntity, JobGroup> {
    @Resource
    private JobRegistryRepository jobRegistryRepository;

    @Override
    public JobGroup copySourceToTarget(JobGroupEntity jobGroupEntity, JobGroup jobGroup) {
        jobGroup.setId(jobGroupEntity.getId());
        jobGroup.setAppname(jobGroupEntity.getAppname());
        jobGroup.setTitle(jobGroupEntity.getTitle());
        jobGroup.setAddressType(jobGroupEntity.getAddressType());
        if (jobGroupEntity.getAddressType()) {
            jobGroup.setAddressList(jobGroupEntity.getJobRegistries().stream()
                    .map(JobRegistryEntity::getRegistryValue).collect(Collectors.joining(",")));
        } else {
            jobGroup.setAddressList(StringUtil.toString(jobGroupEntity.getAddressList()));
        }
        return jobGroup;
    }

    @Override
    public JobGroupEntity copyTargetToSource(JobGroup jobGroup, JobGroupEntity jobGroupEntity) {
        jobGroupEntity.setAppname(jobGroup.getAppname());
        jobGroupEntity.setTitle(jobGroup.getTitle());
        jobGroupEntity.setAddressType(jobGroup.getAddressType());
        if (jobGroup.getAddressType()) {
            jobGroupEntity.setJobRegistries(jobRegistryRepository.getByRegistryKey(jobGroup.getAppname()));
        } else {
            jobGroupEntity.setAddressList(StringUtil.toList(jobGroup.getAddressList()));
        }
        jobGroupEntity.setId(jobGroup.getId());
        return jobGroupEntity;
    }
}
