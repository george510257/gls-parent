package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.JobGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by george on 16/9/30.
 */
@Mapper
public interface JobGroupDao {

    List<JobGroup> findAll();

    List<JobGroup> findByAddressType(@Param("addressType") int addressType);

    int save(JobGroup jobGroup);

    int update(JobGroup jobGroup);

    int remove(@Param("id") Long id);

    JobGroup load(@Param("id") Long id);

    List<JobGroup> pageList(@Param("offset") int offset,
                            @Param("pagesize") int pagesize,
                            @Param("appname") String appname,
                            @Param("title") String title);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("appname") String appname,
                      @Param("title") String title);

}
