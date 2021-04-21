package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.XxlJobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job info
 *
 * @author george 2016-1-12 18:03:45
 */
@Mapper
public interface XxlJobInfoDao {

    List<XxlJobInfo> pageList(@Param("offset") int offset,
                              @Param("pagesize") int pagesize,
                              @Param("jobGroup") int jobGroup,
                              @Param("triggerStatus") int triggerStatus,
                              @Param("jobDesc") String jobDesc,
                              @Param("executorHandler") String executorHandler,
                              @Param("author") String author);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("jobGroup") int jobGroup,
                      @Param("triggerStatus") int triggerStatus,
                      @Param("jobDesc") String jobDesc,
                      @Param("executorHandler") String executorHandler,
                      @Param("author") String author);

    int save(XxlJobInfo info);

    XxlJobInfo loadById(@Param("id") int id);

    int update(XxlJobInfo glsJobInfo);

    int delete(@Param("id") long id);

    List<XxlJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

    int findAllCount();

    List<XxlJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize);

    int scheduleUpdate(XxlJobInfo glsJobInfo);

}
