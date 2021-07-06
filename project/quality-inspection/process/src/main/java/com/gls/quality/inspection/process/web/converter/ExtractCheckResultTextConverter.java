package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExtractCheckResultTextConverter implements BaseConverter<ExtractCheckResultTextEntity, ExtractCheckResultTextModel> {
    @Override
    public ExtractCheckResultTextModel copySourceToTarget(ExtractCheckResultTextEntity entity, ExtractCheckResultTextModel model) {
        // todo
        return model;
    }

    @Override
    public ExtractCheckResultTextEntity copyTargetToSource(ExtractCheckResultTextModel model, ExtractCheckResultTextEntity entity) {
        // todo
        return entity;
    }
}
