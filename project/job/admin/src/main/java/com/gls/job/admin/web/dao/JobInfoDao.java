package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.model.JobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job info
 *
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface JobInfoDao {

    List<JobInfo> pageList(@Param("offset") int offset,
                           @Param("pagesize") int pagesize,
                           @Param("jobGroup") Long jobGroup,
                           @Param("triggerStatus") int triggerStatus,
                           @Param("jobDesc") String jobDesc,
                           @Param("executorHandler") String executorHandler,
                           @Param("author") String author);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("jobGroup") Long jobGroup,
                      @Param("triggerStatus") int triggerStatus,
                      @Param("jobDesc") String jobDesc,
                      @Param("executorHandler") String executorHandler,
                      @Param("author") String author);

    int save(JobInfo info);

    JobInfo loadById(@Param("id") Long id);

    int update(JobInfo jobInfo);

    int delete(@Param("id") long id);

    List<JobInfo> getJobsByGroup(@Param("jobGroup") Long jobGroup);

    int findAllCount();

    List<JobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize);

    int scheduleUpdate(JobInfo jobInfo);

}
