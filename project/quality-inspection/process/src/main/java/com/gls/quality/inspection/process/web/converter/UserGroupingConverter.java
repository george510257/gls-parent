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
    public UserGroupingModel copySourceToTarget(UserGroupingEntity entity, UserGroupingModel model) {
        // todo
        return model;
    }

    @Override
    public UserGroupingEntity copyTargetToSource(UserGroupingModel model, UserGroupingEntity entity) {
        // todo
        return entity;
    }
}
