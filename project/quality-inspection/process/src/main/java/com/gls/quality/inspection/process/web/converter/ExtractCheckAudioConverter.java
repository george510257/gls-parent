package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioConverter implements BaseConverter<ExtractCheckAudioEntity, ExtractCheckAudioModel> {
    @Override
    public ExtractCheckAudioModel copySourceToTarget(ExtractCheckAudioEntity entity, ExtractCheckAudioModel model) {
        return model;
    }

    @Override
    public ExtractCheckAudioEntity copyTargetToSource(ExtractCheckAudioModel model, ExtractCheckAudioEntity entity) {
        return entity;
    }
}
