package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SpotCheckAudioConverter implements BaseConverter<SpotCheckAudioEntity, SpotCheckAudioModel> {
    @Override
    public SpotCheckAudioModel copySourceToTarget(SpotCheckAudioEntity entity, SpotCheckAudioModel model) {
        // todo
        return model;
    }

    @Override
    public SpotCheckAudioEntity copyTargetToSource(SpotCheckAudioModel model, SpotCheckAudioEntity entity) {
        // todo
        return entity;
    }
}
