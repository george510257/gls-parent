package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.web.converter.JobRegistryConverter;
import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.admin.web.model.query.QueryJobRegistry;
import com.gls.job.admin.web.repository.JobRegistryRepository;
import com.gls.job.admin.web.service.JobRegistryService;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.constants.JobConstants;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author george
 */
@Service("jobRegistryService")
public class JobRegistryServiceImpl extends BaseServiceImpl<JobRegistryEntity, RegistryModel, QueryJobRegistry> implements JobRegistryService {
    private final JobRegistryRepository jobRegistryRepository;
    private final JobRegistryConverter jobRegistryConverter;

    public JobRegistryServiceImpl(JobRegistryRepository jobRegistryRepository, JobRegistryConverter jobRegistryConverter) {
        super(jobRegistryRepository, jobRegistryConverter);
        this.jobRegistryRepository = jobRegistryRepository;
        this.jobRegistryConverter = jobRegistryConverter;
    }

    @Override
    public void doJobRegistry() {
        Date date = DateUtil.offsetSecond(new Date(), -JobConstants.DEAD_TIMEOUT);
        jobRegistryRepository.deleteByUpdateDateBefore(date);
    }

    @Override
    public void add(RegistryModel model) {
        super.remove(model);
        super.add(model);
    }

    @Override
    protected Specification<JobRegistryEntity> getSpec(QueryJobRegistry queryJobRegistry) {
        // todo 动态查询
        throw new GlsException("没有查询条件");
    }
}
