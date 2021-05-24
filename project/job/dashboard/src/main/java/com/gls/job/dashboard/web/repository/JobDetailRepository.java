package com.gls.job.dashboard.web.repository;

import com.gls.job.dashboard.web.entity.JobDetailEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface JobDetailRepository extends BaseEntityRepository<JobDetailEntity> {

    /**
     * 通过name和group获取entity对象
     *
     * @param name
     * @param groupName
     * @return
     */
    JobDetailEntity findByNameAndGroupName(String name, String groupName);
}
