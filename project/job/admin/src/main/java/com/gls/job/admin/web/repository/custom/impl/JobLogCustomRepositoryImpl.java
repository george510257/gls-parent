package com.gls.job.admin.web.repository.custom.impl;

import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.custom.JobLogCustomRepository;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
public class JobLogCustomRepositoryImpl extends BaseRepositoryImpl<JobLogEntity, Long> implements JobLogCustomRepository {

    public JobLogCustomRepositoryImpl(JpaEntityInformation<JobLogEntity, Long> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public JobLogCustomRepositoryImpl(Class<JobLogEntity> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<JobLogEntity> loadByCustomParam(Long jobGroupId, Long jobInfoId, Timestamp triggerTimeStart, Timestamp triggerTimeEnd, Integer logStatus, Pageable pageable) {
        Specification<JobLogEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isEmpty(jobInfoId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("id"), jobInfoId));
            }
            if (ObjectUtils.isEmpty(jobGroupId)) {
                predicates.add(criteriaBuilder.equal(root.get("jobInfo").get("jobGroup").get("id"), jobGroupId));
            }
            if (ObjectUtils.isEmpty(triggerTimeStart)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("triggerTime"), triggerTimeStart));
            }
            if (ObjectUtils.isEmpty(triggerTimeEnd)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("triggerTime"), triggerTimeEnd));
            }
            switch (logStatus) {
                case 1:
                    predicates.add(criteriaBuilder.equal(root.get("handleCode"), 200));
                    break;
                case 2:
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.not(criteriaBuilder.in(root.get("handleCode")).in(0, 200)),
                            criteriaBuilder.not(criteriaBuilder.in(root.get("triggerCode")).in(0, 200))
                    ));
                    break;

                case 3:
                    predicates.add(criteriaBuilder.equal(root.get("handleCode"), 0));
                    predicates.add(criteriaBuilder.equal(root.get("triggerCode"), 200));
                    break;
                default:
                    break;
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pageable);
    }

    @Override
    public List<JobLogEntity> findClearLogIds(Long jobGroupId, Long jobInfoId, Timestamp clearBeforeTime, Integer clearBeforeNum, Integer pageSize) {
        return loadByCustomParam(jobGroupId, jobInfoId, null, clearBeforeTime, null, PageRequest.of(clearBeforeNum, pageSize, Sort.by("triggerTime desc"))).getContent();
    }
}
