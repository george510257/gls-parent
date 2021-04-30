package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.JobLogGlue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job log for glue
 *
 * @author george 2016-5-19 18:04:56
 */
@Mapper
public interface JobLogGlueDao {

    int save(JobLogGlue jobLogGlue);

    List<JobLogGlue> findByJobId(@Param("jobId") Long jobId);

    int removeOld(@Param("jobId") Long jobId, @Param("limit") int limit);

    int deleteByJobId(@Param("jobId") Long jobId);

}
