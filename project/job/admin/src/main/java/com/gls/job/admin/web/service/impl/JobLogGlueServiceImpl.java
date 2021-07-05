package com.gls.job.admin.web.service.impl;

import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.converter.JobLogGlueConverter;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.model.JobLogGlue;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.service.JobLogGlueService;
import com.gls.job.core.constants.GlueType;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Service("jobLogGlueService")
public class JobLogGlueServiceImpl
        extends BaseServiceImpl<JobLogGlueRepository, JobLogGlueConverter, JobLogGlueEntity, JobLogGlue, Object>
        implements JobLogGlueService {
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobInfoConverter jobInfoConverter;

    public JobLogGlueServiceImpl(JobLogGlueRepository repository, JobLogGlueConverter converter) {
        super(repository, converter);
    }

    @Override
    public void add(JobLogGlue model) {
        super.add(model);
        repository.deleteOldJobLogGlue(model.getJobId(), 30);
    }

    @Override
    protected Specification<JobLogGlueEntity> getSpec(Object o) {
        return null;
    }

    @Override
    public Map<String, Object> getIndex(Long jobId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        List<JobLogGlueEntity> jobLogGlueEntities = repository.getByJobInfoIdOrderByIdDesc(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new GlsException("任务ID非法");
        }
        if (GlueType.BEAN.equals(jobInfoEntity.getGlueType())) {
            throw new GlsException("该任务非GLUE模式");
        }
        boolean validPermission = LoginUserUtil.validPermission(jobInfoEntity.getJobGroup().getId());
        if (!validPermission) {
            throw new GlsException("权限拦截");
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("GlueType", GlueType.values());
        map.put("jobInfo", jobInfoConverter.sourceToTarget(jobInfoEntity));
        map.put("jobLogGlues", converter.sourceToTargetList(jobLogGlueEntities));
        return map;
    }
}
