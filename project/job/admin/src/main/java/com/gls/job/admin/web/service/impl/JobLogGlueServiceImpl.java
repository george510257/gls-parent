package com.gls.job.admin.web.service.impl;

import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.converter.JobLogGlueConverter;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.service.JobLogGlueService;
import com.gls.job.core.constants.GlueType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Service("jobLogGlueService")
public class JobLogGlueServiceImpl implements JobLogGlueService {
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;
    @Resource
    private JobInfoConverter jobInfoConverter;
    @Resource
    private JobLogGlueConverter jobLogGlueConverter;

    @Override
    public Map<String, Object> getIndex(Long jobId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        List<JobLogGlueEntity> jobLogGlueEntities = jobLogGlueRepository.getByJobInfoIdOrderByIdDesc(jobId);
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
        map.put("jobLogGlues", jobLogGlueConverter.sourceToTargetList(jobLogGlueEntities));
        return map;
    }

    @Override
    public void saveGlueSource(Long jobId, String glueSource, String glueRemark) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            throw new GlsException("任务ID非法");
        }
        jobInfoEntity.setGlueSource(glueSource);
        jobInfoEntity.setGlueRemark(glueRemark);
        jobInfoEntity.setGlueUpdateTime(new Date());
        jobInfoRepository.save(jobInfoEntity);
        JobLogGlueEntity jobLogGlueEntity = new JobLogGlueEntity();
        jobLogGlueEntity.setJobInfo(jobInfoEntity);
        jobLogGlueEntity.setGlueType(jobInfoEntity.getGlueType());
        jobLogGlueEntity.setGlueSource(glueSource);
        jobLogGlueEntity.setGlueRemark(glueRemark);
        jobLogGlueRepository.save(jobLogGlueEntity);
        jobLogGlueRepository.deleteOldJobLogGlue(jobId, 30);
    }
}
