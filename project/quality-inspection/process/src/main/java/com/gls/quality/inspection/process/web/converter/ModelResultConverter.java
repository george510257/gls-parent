package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultEntity;
import com.gls.quality.inspection.process.web.model.ModelResultModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ModelResultConverter implements BaseConverter<ModelResultEntity, ModelResultModel> {
    @Override
    public ModelResultModel copySourceToTarget(ModelResultEntity entity, ModelResultModel model) {
        return model;
    }

    @Override
    public ModelResultEntity copyTargetToSource(ModelResultModel model, ModelResultEntity entity) {
        return entity;
    }
}
