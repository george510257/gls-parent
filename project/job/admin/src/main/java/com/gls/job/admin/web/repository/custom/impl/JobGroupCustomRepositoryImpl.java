package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.repository.custom.JobGroupCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<JobGroupEntity> getAllList() {
        Sort sort = getSort();
        return findAll(sort);
    }

    @Override
    public Page<JobGroupEntity> getPage(String appname, String title, int page, int size) {
        return findAll(getSpecByAppnameAndTitle(appname, title), getPageable(page, size));
    }

    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size, getSort());
    }

    private Specification<JobGroupEntity> getSpecByAppnameAndTitle(String appname, String title) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(appname)) {
                predicates.add(criteriaBuilder.like(root.get("appname"), "%" + appname + "%"));
            }
            if (StringUtils.hasText(title)) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[2]));
        };
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "appname", "title", "id");
    }
}
