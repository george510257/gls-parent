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
public class UserConverter extends BaseConverter<UserEntity, UserModel> {

    @Resource
    private RoleConverter roleConverter;

    @Override
    protected UserModel copySourceToTarget(UserEntity user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setPassword(user.getPassword());
        userModel.setUsername(user.getUsername());
        userModel.setRoles(roleConverter.sourceToTarget(user.getRoles()));
        userModel.setAccountNonExpired(user.getAccountNonExpired());
        userModel.setAccountNonLocked(user.getAccountNonLocked());
        userModel.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userModel.setEnabled(user.getEnabled());
        return userModel;
    }

    @Override
    protected UserEntity copyTargetToSource(UserModel userModel) {
        UserEntity user = new UserEntity();
        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setRoles(roleConverter.targetToSource(userModel.getRoles()));
        user.setAccountNonExpired(userModel.getAccountNonExpired());
        user.setAccountNonLocked(userModel.getAccountNonLocked());
        user.setCredentialsNonExpired(userModel.getCredentialsNonExpired());
        user.setEnabled(userModel.getEnabled());
        user.setId(userModel.getId());
        return user;
    }
}