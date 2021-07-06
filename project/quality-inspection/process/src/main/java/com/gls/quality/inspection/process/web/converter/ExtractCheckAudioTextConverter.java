package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioTextConverter implements BaseConverter<ExtractCheckAudioTextEntity, ExtractCheckAudioTextModel> {
    @Override
    public ExtractCheckAudioTextModel copySourceToTarget(ExtractCheckAudioTextEntity entity, ExtractCheckAudioTextModel model) {
        // todo
        return model;
    }

    @Override
    public ExtractCheckAudioTextEntity copyTargetToSource(ExtractCheckAudioTextModel model, ExtractCheckAudioTextEntity entity) {
        // todo
        return entity;
    }
}
