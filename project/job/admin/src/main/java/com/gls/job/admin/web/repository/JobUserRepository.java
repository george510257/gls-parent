package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobUserEntity;
import com.gls.job.admin.web.repository.custom.JobUserCustomRepository;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface JobUserRepository extends BaseEntityRepository<JobUserEntity>, JobUserCustomRepository {

    /**
     * 根据用户名获取
     *
     * @param username
     * @return
     */
    Optional<JobUserEntity> findByUsername(String username);

}
