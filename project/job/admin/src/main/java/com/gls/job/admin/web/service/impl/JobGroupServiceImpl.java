package com.gls.job.admin.web.service.impl;

import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.web.converter.JobGroupConverter;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.model.JobGroupModel;
import com.gls.job.admin.web.model.query.QueryJobGroup;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.service.JobGroupService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Service("jobGroupService")
public class JobGroupServiceImpl extends BaseServiceImpl<JobGroupRepository, JobGroupConverter, JobGroupEntity, JobGroupModel, QueryJobGroup> implements JobGroupService {
    @Resource
    private JobInfoRepository jobInfoRepository;

    public JobGroupServiceImpl(JobGroupRepository repository, JobGroupConverter converter) {
        super(repository, converter);
    }

    @Override
    public void remove(Long id) {
        List<JobInfoEntity> jobInfoEntities = jobInfoRepository.getByJobGroupId(id);
        if (!ObjectUtils.isEmpty(jobInfoEntities)) {
            throw new GlsException("拒绝删除，该执行器使用中");
        }
        long size = repository.count();
        if (size == 1) {
            throw new GlsException("拒绝删除, 系统至少保留一个执行器");
        }
        super.remove(id);
    }

    @Override
    protected Specification<JobGroupEntity> getSpec(QueryJobGroup queryJobGroup) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(queryJobGroup.getAppname())) {
                predicates.add(criteriaBuilder.like(root.get("appname"), "%" + queryJobGroup.getAppname() + "%"));
            }
            if (StringUtils.hasText(queryJobGroup.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + queryJobGroup.getTitle() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[2]));
        };
    }
}
