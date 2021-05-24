package com.gls.common.user.web.repository;

import com.gls.common.user.web.entity.UserEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface UserRepository extends BaseEntityRepository<UserEntity> {

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    UserEntity getOneByUsername(String username);

    /**
     * 通过用户名删除用户信息
     *
     * @param username
     * @return
     */
    Integer deleteAllByUsername(String username);
}
