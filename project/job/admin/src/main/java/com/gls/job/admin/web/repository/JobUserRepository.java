package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface JobUserRepository {

    public List<JobUserEntity> pageList(@Param("offset") int offset,
                                        @Param("pagesize") int pagesize,
                                        @Param("username") String username,
                                        @Param("role") int role);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("username") String username,
                             @Param("role") int role);

    public JobUserEntity loadByUserName(@Param("username") String username);

    public int save(JobUserEntity jobUserEntity);

    public int update(JobUserEntity jobUserEntity);

    public int delete(@Param("id") int id);

}
