package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.repository.custom.JobInfoCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author george
 */
public interface JobInfoRepository extends BaseEntityRepository<JobInfoEntity>, JobInfoCustomRepository {
    /**
     * get By JobGroupId
     *
     * @param jobGroupId
     * @return
     */
    List<JobInfoEntity> getByJobGroupId(Long jobGroupId);

    /**
     * getScheduleJob
     *
     * @param triggerNextTime
     * @param size
     * @return
     */
    @Query(value = "select t.* from job_info_entity t " +
            "where t.trigger_status = true " +
            "and t.trigger_next_time <= :triggerNextTime " +
            "order by t.id asc " +
            "limit :size", nativeQuery = true)
    List<JobInfoEntity> getScheduleJob(@Param("triggerNextTime") Date triggerNextTime, @Param("size") int size);
}
