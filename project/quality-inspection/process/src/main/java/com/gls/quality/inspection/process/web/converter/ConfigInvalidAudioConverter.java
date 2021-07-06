package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigInvalidAudioEntity;
import com.gls.quality.inspection.process.web.model.ConfigInvalidAudioModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigInvalidAudioConverter implements BaseConverter<ConfigInvalidAudioEntity, ConfigInvalidAudioModel> {
    @Override
    public ConfigInvalidAudioModel copySourceToTarget(ConfigInvalidAudioEntity entity, ConfigInvalidAudioModel model) {
        // todo
        return model;
    }

    @Override
    public ConfigInvalidAudioEntity copyTargetToSource(ConfigInvalidAudioModel model, ConfigInvalidAudioEntity entity) {
        // todo
        return entity;
    }
}
