package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.custom.JobUserCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
public class JobUserCustomRepositoryImpl extends BaseRepositoryImpl<JobUserEntity, Long> implements JobUserCustomRepository {

    public JobUserCustomRepositoryImpl(JpaEntityInformation<JobUserEntity, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public JobUserCustomRepositoryImpl(Class<JobUserEntity> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<JobUserEntity> loadByCustomParam(String username, Integer role, Pageable pageable) {
        Specification<JobUserEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isEmpty(username)) {
                predicates.add(criteriaBuilder.like(root.get("username"), username));
            }
            if (ObjectUtils.isEmpty(role)) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pageable);
    }
}
