package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExtractCheckConverter implements BaseConverter<ExtractCheckEntity, ExtractCheckModel> {
    @Override
    public ExtractCheckModel copySourceToTarget(ExtractCheckEntity entity, ExtractCheckModel model) {
        // todo
        return model;
    }

    @Override
    public ExtractCheckEntity copyTargetToSource(ExtractCheckModel model, ExtractCheckEntity entity) {
        // todo
        return entity;
    }
}
