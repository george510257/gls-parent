package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.repository.custom.JobInfoCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Repository("jobInfoCustomRepository")
public class JobInfoCustomRepositoryImpl extends BaseRepositoryImpl<JobInfoEntity, Long> implements JobInfoCustomRepository {
    public JobInfoCustomRepositoryImpl(EntityManager em) {
        super(JobInfoEntity.class, em);
    }

    @Override
    public Page<JobInfoEntity> getPage(Long jobGroupId, Boolean triggerStatus, String jobDesc, String executorHandler, String author, int page, int size) {
        return findAll(getSpec(jobGroupId, triggerStatus, jobDesc, executorHandler, author), getPageable(page, size));
    }

    private Specification<JobInfoEntity> getSpec(Long jobGroupId, Boolean triggerStatus, String jobDesc, String executorHandler, String author) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(jobGroupId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobGroup").get("id"), jobGroupId));
            }
            if (!ObjectUtils.isEmpty(triggerStatus)) {
                predicates.add(criteriaBuilder.equal(root.get("triggerStatus"), triggerStatus));
            }
            if (StringUtils.hasText(jobDesc)) {
                predicates.add(criteriaBuilder.like(root.get("jobDesc"), "%" + jobDesc + "%"));
            }
            if (StringUtils.hasText(executorHandler)) {
                predicates.add(criteriaBuilder.like(root.get("executorHandler"), "%" + executorHandler + "%"));
            }
            if (StringUtils.hasText(author)) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[5]));
        };
    }

    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }
}
