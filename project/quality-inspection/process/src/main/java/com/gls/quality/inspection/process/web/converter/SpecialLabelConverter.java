package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpecialLabelEntity;
import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SpecialLabelConverter implements BaseConverter<SpecialLabelEntity, SpecialLabelModel> {
    @Override
    public SpecialLabelModel copySourceToTarget(SpecialLabelEntity entity, SpecialLabelModel model) {
        return model;
    }

    @Override
    public SpecialLabelEntity copyTargetToSource(SpecialLabelModel model, SpecialLabelEntity entity) {
        return entity;
    }
}
