package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.custom.JobLogCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
public class JobLogCustomRepositoryImpl extends BaseRepositoryImpl<JobLogEntity, Long> implements JobLogCustomRepository {

    public JobLogCustomRepositoryImpl(JpaEntityInformation<JobLogEntity, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public JobLogCustomRepositoryImpl(Class<JobLogEntity> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<JobLogEntity> getPage(Long jobGroupId, Long jobInfoId, Date triggerTimeFrom, Date triggerTimeTo, Integer logStatus, int page, int size) {
        return findAll(getSpec(jobGroupId, jobInfoId, triggerTimeFrom, triggerTimeTo, logStatus), getPageable(page, size));
    }

    private Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size, getSort());
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "triggerTime");
    }

    private Specification<JobLogEntity> getSpec(Long jobGroupId, Long jobInfoId, Date triggerTimeFrom, Date triggerTimeTo, Integer logStatus) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(jobGroupId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("jobGroup").get("id"), jobGroupId));
            }
            if (!ObjectUtils.isEmpty(jobInfoId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("id"), jobInfoId));
            }
            if (!ObjectUtils.isEmpty(triggerTimeFrom)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("triggerTime"), triggerTimeFrom));
            }
            if (!ObjectUtils.isEmpty(triggerTimeTo)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("triggerTime"), triggerTimeTo));
            }
            switch (logStatus) {
                case 1:
                    predicates.add(criteriaBuilder.equal(root.get("handleCode"), 200));
                    break;
                case 2:
                    Predicate predicate1 = criteriaBuilder.not(root.get("triggerCode").in(0, 200));
                    Predicate predicate2 = criteriaBuilder.not(root.get("handleCode").in(0, 200));
                    predicates.add(criteriaBuilder.or(predicate1, predicate2));
                    break;
                case 3:
                    predicates.add(criteriaBuilder.equal(root.get("triggerCode"), 200));
                    predicates.add(criteriaBuilder.equal(root.get("handleCode"), 0));
                    break;
                default:
                    log.info("logStatus : {}", logStatus);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
