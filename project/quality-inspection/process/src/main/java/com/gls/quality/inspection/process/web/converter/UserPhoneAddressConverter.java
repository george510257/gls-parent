package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.UserPhoneAddressEntity;
import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class UserPhoneAddressConverter implements BaseConverter<UserPhoneAddressEntity, UserPhoneAddressModel> {
    @Override
    public UserPhoneAddressModel copySourceToTarget(UserPhoneAddressEntity entity, UserPhoneAddressModel model) {
        return model;
    }

    @Override
    public UserPhoneAddressEntity copyTargetToSource(UserPhoneAddressModel model, UserPhoneAddressEntity entity) {
        return entity;
    }
}
