package com.gls.common.user.web.converter;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.web.entity.UserEntity;
import com.gls.framework.core.base.BaseConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class UserConverter implements BaseConverter<UserEntity, UserModel> {
    @Resource
    private RoleConverter roleConverter;

    @Override
    public UserModel copySourceToTarget(UserEntity userEntity, UserModel userModel) {
        userModel.setId(userEntity.getId());
        userModel.setPassword(userEntity.getPassword());
        userModel.setUsername(userEntity.getUsername());
        userModel.setRoles(roleConverter.sourceToTargetList(userEntity.getRoles()));
        userModel.setAccountNonExpired(userEntity.getAccountNonExpired());
        userModel.setAccountNonLocked(userEntity.getAccountNonLocked());
        userModel.setCredentialsNonExpired(userEntity.getCredentialsNonExpired());
        userModel.setEnabled(userEntity.getEnabled());
        return userModel;
    }

    @Override
    public UserEntity copyTargetToSource(UserModel userModel, UserEntity userEntity) {
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setRoles(roleConverter.targetToSourceList(userModel.getRoles()));
        userEntity.setAccountNonExpired(userModel.getAccountNonExpired());
        userEntity.setAccountNonLocked(userModel.getAccountNonLocked());
        userEntity.setCredentialsNonExpired(userModel.getCredentialsNonExpired());
        userEntity.setEnabled(userModel.getEnabled());
        userEntity.setId(userModel.getId());
        return userEntity;
    }
}