package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.repository.custom.JobGroupCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
public class JobGroupCustomRepositoryImpl extends BaseRepositoryImpl<JobGroupEntity, Long> implements JobGroupCustomRepository {

    public JobGroupCustomRepositoryImpl(JpaEntityInformation<JobGroupEntity, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public JobGroupCustomRepositoryImpl(Class<JobGroupEntity> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<JobGroupEntity> loadByCustomParam(String appname, String title, Pageable pageable) {
        Specification<JobGroupEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(appname)) {
                predicates.add(criteriaBuilder.like(root.get("appname"), appname));
            }
            if (StringUtils.hasLength(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), title));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pageable);
    }
}
