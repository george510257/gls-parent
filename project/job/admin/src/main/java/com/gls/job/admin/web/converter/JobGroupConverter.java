package com.gls.job.admin.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.utils.StringUtils;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.model.JobGroup;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class JobGroupConverter implements BaseConverter<JobGroupEntity, JobGroup> {

    @Override
    public JobGroup copySourceToTarget(JobGroupEntity jobGroupEntity, JobGroup jobGroup) {
        jobGroup.setId(jobGroupEntity.getId());
        jobGroup.setAppname(jobGroupEntity.getAppname());
        jobGroup.setTitle(jobGroupEntity.getTitle());
        jobGroup.setAddressType(jobGroupEntity.getAddressType() ? 0 : 1);
        jobGroup.setAddressList(StringUtils.toString(jobGroupEntity.getAddressList()));
        jobGroup.setUpdateTime(jobGroupEntity.getUpdateDate());
        jobGroup.setRegistryList(jobGroupEntity.getAddressList());
        return jobGroup;
    }

    @Override
    public JobGroupEntity copyTargetToSource(JobGroup jobGroup, JobGroupEntity jobGroupEntity) {
        jobGroupEntity.setAppname(jobGroup.getAppname());
        jobGroupEntity.setTitle(jobGroup.getTitle());
        jobGroupEntity.setAddressType(jobGroup.getAddressType() == 0);
        jobGroupEntity.setAddressList(StringUtils.toList(jobGroup.getAddressList()));
        jobGroupEntity.setId(jobGroup.getId());
        return jobGroupEntity;
    }
}
