package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExtractCheckResultConverter implements BaseConverter<ExtractCheckResultEntity, ExtractCheckResultModel> {
    @Override
    public ExtractCheckResultModel copySourceToTarget(ExtractCheckResultEntity entity, ExtractCheckResultModel model) {
        return model;
    }

    @Override
    public ExtractCheckResultEntity copyTargetToSource(ExtractCheckResultModel model, ExtractCheckResultEntity entity) {
        return entity;
    }
}
