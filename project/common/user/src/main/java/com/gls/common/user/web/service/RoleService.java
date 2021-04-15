package com.gls.common.user.web.service;

import com.gls.common.user.web.entity.RoleEntity;

import java.util.List;

/**
 * @author george
 */
public interface RoleService {

    /**
     * 加载角色信息
     *
     * @param roles
     * @return
     */
    List<RoleEntity> loadRoles(List<RoleEntity> roles);
}
