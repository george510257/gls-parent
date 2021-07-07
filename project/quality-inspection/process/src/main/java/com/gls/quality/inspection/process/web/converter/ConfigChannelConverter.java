package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigChannelEntity;
import com.gls.quality.inspection.process.web.model.ConfigChannelModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigChannelConverter implements BaseConverter<ConfigChannelEntity, ConfigChannelModel> {
    @Override
    public ConfigChannelModel copySourceToTarget(ConfigChannelEntity configChannelEntity, ConfigChannelModel configChannelModel) {
        configChannelModel.setDoubleChannelRadio(configChannelEntity.getDoubleChannelRadio());
        configChannelModel.setDoubleChannelDefault(configChannelEntity.getDoubleChannelDefault());
        configChannelModel.setSingleChannelRadio(configChannelEntity.getSingleChannelRadio());
        configChannelModel.setSingleChannelText(configChannelEntity.getSingleChannelText());
        configChannelModel.setId(configChannelEntity.getId());
        return configChannelModel;
    }

    @Override
    public ConfigChannelEntity copyTargetToSource(ConfigChannelModel configChannelModel, ConfigChannelEntity configChannelEntity) {
        configChannelEntity.setDoubleChannelRadio(configChannelModel.getDoubleChannelRadio());
        configChannelEntity.setDoubleChannelDefault(configChannelModel.getDoubleChannelDefault());
        configChannelEntity.setSingleChannelRadio(configChannelModel.getSingleChannelRadio());
        configChannelEntity.setSingleChannelText(configChannelModel.getSingleChannelText());
        configChannelEntity.setId(configChannelModel.getId());
        return configChannelEntity;
    }
}