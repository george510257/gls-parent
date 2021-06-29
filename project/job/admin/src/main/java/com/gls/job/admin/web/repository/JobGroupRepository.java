package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

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
}
