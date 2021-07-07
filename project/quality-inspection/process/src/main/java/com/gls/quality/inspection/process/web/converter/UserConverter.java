package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.UserEntity;
import com.gls.quality.inspection.process.web.model.UserModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class UserConverter implements BaseConverter<UserEntity, UserModel> {
    @Override
    public UserModel copySourceToTarget(UserEntity userEntity, UserModel userModel) {
        userModel.setUsername(userEntity.getUsername());
        userModel.setPasswordHash(userEntity.getPasswordHash());
        userModel.setStatus(userEntity.getStatus());
        userModel.setPortrait(userEntity.getPortrait());
        userModel.setUserRole(userEntity.getUserRole());
        userModel.setUserGroupingId(userEntity.getUserGroupingId());
        userModel.setUserGroupingIds(userEntity.getUserGroupingIds());
        userModel.setMobile(userEntity.getMobile());
        userModel.setCustomerServiceId(userEntity.getCustomerServiceId());
        userModel.setRealName(userEntity.getRealName());
        userModel.setPasswordStrength(userEntity.getPasswordStrength());
        userModel.setIsGrouped(userEntity.getIsGrouped());
        userModel.setId(userEntity.getId());
        return userModel;
    }

    @Override
    public UserEntity copyTargetToSource(UserModel userModel, UserEntity userEntity) {
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPasswordHash(userModel.getPasswordHash());
        userEntity.setStatus(userModel.getStatus());
        userEntity.setPortrait(userModel.getPortrait());
        userEntity.setUserRole(userModel.getUserRole());
        userEntity.setUserGroupingId(userModel.getUserGroupingId());
        userEntity.setUserGroupingIds(userModel.getUserGroupingIds());
        userEntity.setMobile(userModel.getMobile());
        userEntity.setCustomerServiceId(userModel.getCustomerServiceId());
        userEntity.setRealName(userModel.getRealName());
        userEntity.setPasswordStrength(userModel.getPasswordStrength());
        userEntity.setIsGrouped(userModel.getIsGrouped());
        userEntity.setId(userModel.getId());
        return userEntity;
    }
}