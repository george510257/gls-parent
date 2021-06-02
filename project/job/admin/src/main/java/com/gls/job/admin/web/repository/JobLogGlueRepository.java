package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * job log for glue
 *
 * @author xuxueli 2016-5-19 18:04:56
 */
public interface JobLogGlueRepository extends BaseEntityRepository<JobLogGlueEntity> {

    /**
     * 根据jobInfoId获取
     *
     * @param jobInfoId
     * @return
     */
    List<JobLogGlueEntity> findByJobInfoId(Long jobInfoId);

    /**
     * 根据jobInfoId获取
     *
     * @param jobInfoId
     * @param pageable
     * @return
     */
    Page<JobLogGlueEntity> findByJobInfoIdOrderByTimeVersionDesc(Long jobInfoId, Pageable pageable);
}
