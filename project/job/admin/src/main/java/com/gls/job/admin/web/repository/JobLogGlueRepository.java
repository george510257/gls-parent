package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author george
 */
public interface JobLogGlueRepository extends BaseEntityRepository<JobLogGlueEntity> {
    /**
     * delete By JobInfoId
     *
     * @param jobInfoId
     */
    void deleteByJobInfoId(Long jobInfoId);

    /**
     * get Old JobLogGlue
     *
     * @param jobInfoId
     * @param size
     * @return
     */
    @Query(value = "delete from job_log_glue_entity t " +
            "where t.job_info_id = :jobInfoId " +
            "and t.update_date < " +
            "(select MIN(t1.update_date) from " +
            "(select t2.update_date from job_log_glue_entity t2 " +
            "where t2.job_info_id = :jobInfoId " +
            "order by t2.update_date desc " +
            "limit :size) as t1);", nativeQuery = true)
    void deleteOldJobLogGlue(@Param("jobInfoId") Long jobInfoId, @Param("size") int size);

    /**
     * get By JobInfoId
     *
     * @param jobInfoId
     * @return
     */
    List<JobLogGlueEntity> getByJobInfoIdOrderByIdDesc(Long jobInfoId);

    /**
     * get Top By JobInfoId Order By UpdateDate Desc
     *
     * @param jobInfoId
     * @return
     */
    JobLogGlueEntity getTopByJobInfoIdOrderByUpdateDateDesc(Long jobInfoId);
}
