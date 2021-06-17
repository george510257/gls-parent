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
     * get By UpdateDate After
     *
     * @param updateDate
     * @return
     */
    List<JobRegistryEntity> getByUpdateDateAfter(Date updateDate);

    /**
     * get By UpdateDate Before
     *
     * @param updateDate
     * @return
     */
    List<JobRegistryEntity> getByUpdateDateBefore(Date updateDate);
}
