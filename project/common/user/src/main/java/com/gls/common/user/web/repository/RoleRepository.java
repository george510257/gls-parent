package com.gls.common.user.web.repository;

import com.gls.common.user.web.entity.RoleEntity;
import com.gls.starter.data.jpa.base.BaseRepository;

/**
 * @author george
 */
public interface RoleRepository extends BaseRepository<RoleEntity, Long> {

    /**
     * 通过role获取Role对象
     *
     * @param role
     * @return
     */
    RoleEntity getOneByRole(String role);
}