package com.gls.common.user.web.repository;

import com.gls.common.user.web.entity.RoleEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface RoleRepository extends BaseEntityRepository<RoleEntity> {

    /**
     * 通过role获取Role对象
     *
     * @param role
     * @return
     */
    RoleEntity getOneByRole(String role);
}