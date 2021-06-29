package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.model.JobGroupModel;
import com.gls.job.admin.web.repository.JobRegistryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Component
public class JobGroupConverter implements BaseConverter<JobGroupEntity, JobGroupModel> {
    @Resource
    private JobRegistryRepository jobRegistryRepository;

    @Override
    public JobGroupModel copySourceToTarget(JobGroupEntity jobGroupEntity, JobGroupModel jobGroupModel) {
        jobGroupModel.setId(jobGroupEntity.getId());
        jobGroupModel.setAppname(jobGroupEntity.getAppname());
        jobGroupModel.setTitle(jobGroupEntity.getTitle());
        jobGroupModel.setAddressType(jobGroupEntity.getAddressType());
        if (jobGroupEntity.getAddressType()) {
            jobGroupModel.setAddressList(jobGroupEntity.getJobRegistries().stream()
                    .map(JobRegistryEntity::getRegistryValue).collect(Collectors.joining(",")));
        } else {
            jobGroupModel.setAddressList(StringUtil.toString(jobGroupEntity.getAddressList()));
        }
        return jobGroupModel;
    }

    @Override
    public JobGroupEntity copyTargetToSource(JobGroupModel jobGroupModel, JobGroupEntity jobGroupEntity) {
        jobGroupEntity.setAppname(jobGroupModel.getAppname());
        jobGroupEntity.setTitle(jobGroupModel.getTitle());
        jobGroupEntity.setAddressType(jobGroupModel.getAddressType());
        if (jobGroupModel.getAddressType()) {
            jobGroupEntity.setJobRegistries(jobRegistryRepository.getByRegistryKey(jobGroupModel.getAppname()));
        } else {
            jobGroupEntity.setAddressList(StringUtil.toList(jobGroupModel.getAddressList()));
        }
        jobGroupEntity.setId(jobGroupModel.getId());
        return jobGroupEntity;
    }
}
