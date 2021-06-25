package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.custom.JobUserCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Repository
public class JobUserCustomRepositoryImpl extends BaseRepositoryImpl<JobUserEntity, Long> implements JobUserCustomRepository {

    public JobUserCustomRepositoryImpl(EntityManager em) {
        super(JobUserEntity.class, em);
    }

    @Override
    public Page<JobUserEntity> getPage(String username, Integer role, int page, int size) {
        return findAll(getSpec(username, role), getPageable(page, size));
    }

    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size, getSort());
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "username");
    }

    private Specification<JobUserEntity> getSpec(String username, Integer role) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(username)) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (!ObjectUtils.isEmpty(role)) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[2]));
        };
    }
}
