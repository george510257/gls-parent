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
    public ConfigInvalidAudioModel copySourceToTarget(ConfigInvalidAudioEntity configInvalidAudioEntity, ConfigInvalidAudioModel configInvalidAudioModel) {
        configInvalidAudioModel.setDefineDurationRadio(configInvalidAudioEntity.getDefineDurationRadio());
        configInvalidAudioModel.setDefineDurationSecond(configInvalidAudioEntity.getDefineDurationSecond());
        configInvalidAudioModel.setDefineResultRadio(configInvalidAudioEntity.getDefineResultRadio());
        configInvalidAudioModel.setDefineRoundRadio(configInvalidAudioEntity.getDefineRoundRadio());
        configInvalidAudioModel.setDefineRoundCount(configInvalidAudioEntity.getDefineRoundCount());
        configInvalidAudioModel.setId(configInvalidAudioEntity.getId());
        return configInvalidAudioModel;
    }

    @Override
    public ConfigInvalidAudioEntity copyTargetToSource(ConfigInvalidAudioModel configInvalidAudioModel, ConfigInvalidAudioEntity configInvalidAudioEntity) {
        configInvalidAudioEntity.setDefineDurationRadio(configInvalidAudioModel.getDefineDurationRadio());
        configInvalidAudioEntity.setDefineDurationSecond(configInvalidAudioModel.getDefineDurationSecond());
        configInvalidAudioEntity.setDefineResultRadio(configInvalidAudioModel.getDefineResultRadio());
        configInvalidAudioEntity.setDefineRoundRadio(configInvalidAudioModel.getDefineRoundRadio());
        configInvalidAudioEntity.setDefineRoundCount(configInvalidAudioModel.getDefineRoundCount());
        configInvalidAudioEntity.setId(configInvalidAudioModel.getId());
        return configInvalidAudioEntity;
    }
}