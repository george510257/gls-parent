package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultTextEntity;
import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ModelResultTextConverter implements BaseConverter<ModelResultTextEntity, ModelResultTextModel> {
    @Override
    public ModelResultTextModel copySourceToTarget(ModelResultTextEntity entity, ModelResultTextModel model) {
        return model;
    }

    @Override
    public ModelResultTextEntity copyTargetToSource(ModelResultTextModel model, ModelResultTextEntity entity) {
        return entity;
    }
}
