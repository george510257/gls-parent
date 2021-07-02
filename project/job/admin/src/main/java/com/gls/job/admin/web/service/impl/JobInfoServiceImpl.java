package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.support.JobScheduleHelper;
import com.gls.job.admin.web.converter.JobInfoConverter;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.query.QueryJobInfo;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.core.constants.JobConstants;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service("jobInfoService")
public class JobInfoServiceImpl extends BaseServiceImpl<JobInfoRepository, JobInfoConverter, JobInfoEntity, JobInfo, QueryJobInfo> implements JobInfoService {
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;

    public JobInfoServiceImpl(JobInfoRepository repository, JobInfoConverter converter) {
        super(repository, converter);
    }

    @Override
    public void update(JobInfo model) {
        JobScheduleHelper.refreshNextValidTime(model, new Date(System.currentTimeMillis() + JobConstants.PRE_READ_MS));
        super.update(model);
    }

    @Override
    public void remove(Long id) {
        super.remove(id);
        jobLogRepository.deleteByJobInfoId(id);
        jobLogGlueRepository.deleteByJobInfoId(id);
    }

    @Override
    protected Specification<JobInfoEntity> getSpec(QueryJobInfo queryJobInfo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(queryJobInfo.getJobGroupId())) {
                predicates.add(criteriaBuilder.equal(root.get("jobGroup").get("id"), queryJobInfo.getJobGroupId()));
            }
            if (!ObjectUtils.isEmpty(queryJobInfo.getTriggerStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("triggerStatus"), queryJobInfo.getTriggerStatus()));
            }
            if (StringUtils.hasText(queryJobInfo.getJobDesc())) {
                predicates.add(criteriaBuilder.like(root.get("jobDesc"), "%" + queryJobInfo.getJobDesc() + "%"));
            }
            if (StringUtils.hasText(queryJobInfo.getExecutorHandler())) {
                predicates.add(criteriaBuilder.like(root.get("executorHandler"), "%" + queryJobInfo.getExecutorHandler() + "%"));
            }
            if (StringUtils.hasText(queryJobInfo.getAuthor())) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + queryJobInfo.getAuthor() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[5]));
        };
    }

    @Override
    public void start(Long jobInfoId) {
        JobInfo jobInfo = getById(jobInfoId);
        update(jobInfo);
    }

    @Override
    public void stop(Long jobInfoId) {
        JobInfoEntity jobInfoEntity = repository.getOne(jobInfoId);
        jobInfoEntity.setTriggerStatus(false);
        jobInfoEntity.setTriggerLastTime(null);
        jobInfoEntity.setTriggerNextTime(null);
        repository.save(jobInfoEntity);
    }

    @Override
    public List<JobInfo> getByJobGroupId(Long jobGroupId) {
        return converter.sourceToTargetList(repository.getByJobGroupId(jobGroupId));
    }

    @Override
    public List<JobInfo> getScheduleJob(Date date, int preReadCount) {
        return converter.sourceToTargetList(repository.getScheduleJob(date, preReadCount));
    }
}
