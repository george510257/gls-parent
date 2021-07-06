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
    public UserModel copySourceToTarget(UserEntity entity, UserModel model) {
        // todo
        return model;
    }

    @Override
    public UserEntity copyTargetToSource(UserModel model, UserEntity entity) {
        // todo
        return entity;
    }
}
