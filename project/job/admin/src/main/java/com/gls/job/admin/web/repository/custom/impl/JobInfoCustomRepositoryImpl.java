package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.repository.custom.JobInfoCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
public class JobInfoCustomRepositoryImpl extends BaseRepositoryImpl<JobInfoEntity, Long> implements JobInfoCustomRepository {

    public JobInfoCustomRepositoryImpl(JpaEntityInformation<JobInfoEntity, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public JobInfoCustomRepositoryImpl(Class<JobInfoEntity> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<JobInfoEntity> loadByCustomParam(Long jobGroupId, Boolean triggerStatus, String jobDesc, String executorHandler, String author, Pageable pageable) {
        Specification<JobInfoEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isEmpty(jobGroupId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobGroup").get("id"), jobGroupId));
            }
            if (ObjectUtils.isEmpty(triggerStatus)) {
                predicates.add(criteriaBuilder.equal(root.get("triggerStatus"), triggerStatus));
            }
            if (StringUtils.hasLength(jobDesc)) {
                predicates.add(criteriaBuilder.like(root.get("jobDesc"), jobDesc));
            }
            if (StringUtils.hasLength(executorHandler)) {
                predicates.add(criteriaBuilder.like(root.get("executorHandler"), executorHandler));
            }
            if (StringUtils.hasLength(author)) {
                predicates.add(criteriaBuilder.like(root.get("author"), author));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pageable);
    }
}
