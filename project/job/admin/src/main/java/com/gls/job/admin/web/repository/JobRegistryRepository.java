package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

import java.util.Date;
import java.util.List;

/**
 * @author george
 */
public interface JobRegistryRepository extends BaseEntityRepository<JobRegistryEntity> {
    /**
     * delete By UpdateDateBefore
     *
     * @param updateDate
     */
    void deleteByUpdateDateBefore(Date updateDate);

    /**
     * get By RegistryKey
     *
     * @param registryKey
     * @return
     */
    List<JobRegistryEntity> getByRegistryKey(String registryKey);
}
