package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobLogGlueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job log for glue
 *
 * @author xuxueli 2016-5-19 18:04:56
 */
@Mapper
public interface JobLogGlueRepository {

    public int save(JobLogGlueEntity jobLogGlueEntity);

    public List<JobLogGlueEntity> findByJobId(@Param("jobId") int jobId);

    public int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

    public int deleteByJobId(@Param("jobId") int jobId);

}
