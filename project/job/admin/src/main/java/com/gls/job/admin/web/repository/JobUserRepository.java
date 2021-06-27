package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.custom.JobUserCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface JobUserRepository extends BaseEntityRepository<JobUserEntity>, JobUserCustomRepository {
    /**
     * get By Username
     *
     * @param username
     * @return
     */
    JobUserEntity getByUsername(String username);
}
