package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.repository.custom.JobGroupCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

import java.util.List;

/**
 * @author xuxueli
 * @date 16/9/30
 */
public interface JobGroupRepository extends BaseEntityRepository<JobGroupEntity>, JobGroupCustomRepository {

    /**
     * 获取所有
     *
     * @return
     */
    List<JobGroupEntity> findAllByOrderByAppnameAscTitleAscIdAsc();

    /**
     * 跟进注册类型获取所有的执行器信息
     *
     * @param addressType 注册类型
     * @return
     */
    List<JobGroupEntity> findByAddressTypeOrderByAppnameAscTitleAscIdAsc(Boolean addressType);

}
