package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.model.JobGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface JobGroupDao {

    public List<JobGroup> findAll();

    public List<JobGroup> findByAddressType(@Param("addressType") int addressType);

    public int save(JobGroup jobGroup);

    public int update(JobGroup jobGroup);

    public int remove(@Param("id") Long id);

    public JobGroup load(@Param("id") Long id);

    public List<JobGroup> pageList(@Param("offset") int offset,
                                   @Param("pagesize") int pagesize,
                                   @Param("appname") String appname,
                                   @Param("title") String title);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title);

}
