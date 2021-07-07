package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.UserGroupingEntity;
import com.gls.quality.inspection.process.web.model.UserGroupingModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class UserGroupingConverter implements BaseConverter<UserGroupingEntity, UserGroupingModel> {
    @Override
    public UserGroupingModel copySourceToTarget(UserGroupingEntity userGroupingEntity, UserGroupingModel userGroupingModel) {
        userGroupingModel.setParentId(userGroupingEntity.getParentId());
        userGroupingModel.setDisplay(userGroupingEntity.getDisplay());
        userGroupingModel.setLevel(userGroupingEntity.getLevel());
        userGroupingModel.setIsShow(userGroupingEntity.getIsShow());
        userGroupingModel.setId(userGroupingEntity.getId());
        return userGroupingModel;
    }

    @Override
    public UserGroupingEntity copyTargetToSource(UserGroupingModel userGroupingModel, UserGroupingEntity userGroupingEntity) {
        userGroupingEntity.setParentId(userGroupingModel.getParentId());
        userGroupingEntity.setDisplay(userGroupingModel.getDisplay());
        userGroupingEntity.setLevel(userGroupingModel.getLevel());
        userGroupingEntity.setIsShow(userGroupingModel.getIsShow());
        userGroupingEntity.setId(userGroupingModel.getId());
        return userGroupingEntity;
    }
}