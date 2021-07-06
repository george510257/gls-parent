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
    public ConfigChannelModel copySourceToTarget(ConfigChannelEntity entity, ConfigChannelModel model) {
        return model;
    }

    @Override
    public ConfigChannelEntity copyTargetToSource(ConfigChannelModel model, ConfigChannelEntity entity) {
        return entity;
    }
}
