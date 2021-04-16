package com.gls.job.admin.dao;

import com.gls.job.admin.core.model.XxlJobLogGlue;
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

    public int save(XxlJobLogGlue glsJobLogGlue);

    public List<XxlJobLogGlue> findByJobId(@Param("jobId") int jobId);

    public int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

    public int deleteByJobId(@Param("jobId") int jobId);

}
