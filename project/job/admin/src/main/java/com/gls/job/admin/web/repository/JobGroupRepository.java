package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface JobGroupRepository {

    public List<JobGroupEntity> findAll();

    public List<JobGroupEntity> findByAddressType(@Param("addressType") int addressType);

    public int save(JobGroupEntity jobGroupEntity);

    public int update(JobGroupEntity jobGroupEntity);

    public int remove(@Param("id") int id);

    public JobGroupEntity load(@Param("id") int id);

    public List<JobGroupEntity> pageList(@Param("offset") int offset,
                                         @Param("pagesize") int pagesize,
                                         @Param("appname") String appname,
                                         @Param("title") String title);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title);

}
