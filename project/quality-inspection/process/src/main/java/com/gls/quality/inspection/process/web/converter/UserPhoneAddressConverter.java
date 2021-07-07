package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.UserPhoneAddressEntity;
import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class UserPhoneAddressConverter implements BaseConverter<UserPhoneAddressEntity, UserPhoneAddressModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;

    @Override
    public UserPhoneAddressModel copySourceToTarget(UserPhoneAddressEntity userPhoneAddressEntity, UserPhoneAddressModel userPhoneAddressModel) {
        userPhoneAddressModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(userPhoneAddressEntity.getExtractCheckAudio()));
        userPhoneAddressModel.setAddress(userPhoneAddressEntity.getAddress());
        userPhoneAddressModel.setId(userPhoneAddressEntity.getId());
        return userPhoneAddressModel;
    }

    @Override
    public UserPhoneAddressEntity copyTargetToSource(UserPhoneAddressModel userPhoneAddressModel, UserPhoneAddressEntity userPhoneAddressEntity) {
        userPhoneAddressEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(userPhoneAddressModel.getExtractCheckAudio()));
        userPhoneAddressEntity.setAddress(userPhoneAddressModel.getAddress());
        userPhoneAddressEntity.setId(userPhoneAddressModel.getId());
        return userPhoneAddressEntity;
    }
}