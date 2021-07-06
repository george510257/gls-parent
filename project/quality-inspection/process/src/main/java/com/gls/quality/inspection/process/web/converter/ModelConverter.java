package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelEntity;
import com.gls.quality.inspection.process.web.model.ModelModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ModelConverter implements BaseConverter<ModelEntity, ModelModel> {
    @Override
    public ModelModel copySourceToTarget(ModelEntity entity, ModelModel model) {
        // todo
        return model;
    }

    @Override
    public ModelEntity copyTargetToSource(ModelModel model, ModelEntity entity) {
        // todo
        return entity;
    }
}
