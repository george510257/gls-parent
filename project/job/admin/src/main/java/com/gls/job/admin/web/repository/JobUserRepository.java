package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface JobUserRepository extends BaseEntityRepository<JobUserEntity> {

    /**
     * get By Username
     *
     * @param username
     * @return
     */
    JobUserEntity getByUsername(String username);
}
