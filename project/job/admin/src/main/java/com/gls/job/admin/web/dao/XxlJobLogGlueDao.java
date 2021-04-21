package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.XxlJobLogGlue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job log for glue
 *
 * @author george 2016-5-19 18:04:56
 */
@Mapper
public interface XxlJobLogGlueDao {

    int save(XxlJobLogGlue glsJobLogGlue);

    List<XxlJobLogGlue> findByJobId(@Param("jobId") int jobId);

    int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

    int deleteByJobId(@Param("jobId") int jobId);

}
