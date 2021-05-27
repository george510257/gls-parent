package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job info
 *
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface JobInfoRepository {

    public List<JobInfoEntity> pageList(@Param("offset") int offset,
                                        @Param("pagesize") int pagesize,
                                        @Param("jobGroup") int jobGroup,
                                        @Param("triggerStatus") int triggerStatus,
                                        @Param("jobDesc") String jobDesc,
                                        @Param("executorHandler") String executorHandler,
                                        @Param("author") String author);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("jobGroup") int jobGroup,
                             @Param("triggerStatus") int triggerStatus,
                             @Param("jobDesc") String jobDesc,
                             @Param("executorHandler") String executorHandler,
                             @Param("author") String author);

    public int save(JobInfoEntity info);

    public JobInfoEntity loadById(@Param("id") int id);

    public int update(JobInfoEntity jobInfoEntity);

    public int delete(@Param("id") long id);

    public List<JobInfoEntity> getJobsByGroup(@Param("jobGroup") int jobGroup);

    public int findAllCount();

    public List<JobInfoEntity> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize);

    public int scheduleUpdate(JobInfoEntity jobInfoEntity);

}
