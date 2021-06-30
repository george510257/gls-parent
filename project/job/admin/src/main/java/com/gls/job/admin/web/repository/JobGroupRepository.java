package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author george
 */
public interface JobGroupRepository extends BaseEntityRepository<JobGroupEntity> {
    /**
     * getByAppname
     *
     * @param appname
     * @return
     */
    JobGroupEntity getByAppname(String appname);

    /**
     * 根据用户权限获取执行器列表
     *
     * @param jobUserId
     * @return
     */
    @Query(value = "select t.jobGroups from JobUserEntity t where t.id = :jobUserId")
    List<JobGroupEntity> getByRoleJobUserId(@Param("jobUserId") Long jobUserId);
}
