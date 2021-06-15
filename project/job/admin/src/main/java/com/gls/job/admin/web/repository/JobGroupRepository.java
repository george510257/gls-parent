package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.repository.custom.JobGroupCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

import java.util.List;

/**
 * @author george
 */
public interface JobGroupRepository extends BaseEntityRepository<JobGroupEntity>, JobGroupCustomRepository {

    /**
     * find By AddressType Order By Appname Asc Title Asc Id Asc
     *
     * @param addressType
     * @return
     */
    List<JobGroupEntity> getByAddressTypeOrderByAppnameAscTitleAscIdAsc(Boolean addressType);

}
