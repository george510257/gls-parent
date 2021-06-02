package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.repository.custom.JobInfoCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * job info
 *
 * @author xuxueli 2016-1-12 18:03:45
 */
public interface JobInfoRepository extends BaseEntityRepository<JobInfoEntity>, JobInfoCustomRepository {

    /**
     * 根据jobGroupId获取
     *
     * @param jobGroupId
     * @return
     */
    List<JobInfoEntity> findByJobGroupId(Long jobGroupId);

    /**
     * 根据maxNextTime获取
     *
     * @param maxNextTime
     * @param pageable
     * @return
     */
    @Query(value = "select jobInfo from JobInfoEntity jobInfo " +
            "where jobInfo.triggerStatus = true " +
            "and jobInfo.triggerNextTime <= :maxNextTime")
    Page<JobInfoEntity> findByMaxNextTime(@Param("maxNextTime") Timestamp maxNextTime, Pageable pageable);
}
