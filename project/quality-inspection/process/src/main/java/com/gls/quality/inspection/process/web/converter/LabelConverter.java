package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LabelEntity;
import com.gls.quality.inspection.process.web.model.LabelModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class LabelConverter implements BaseConverter<LabelEntity, LabelModel> {
    @Override
    public LabelModel copySourceToTarget(LabelEntity entity, LabelModel model) {
        // todo
        return model;
    }

    @Override
    public LabelEntity copyTargetToSource(LabelModel model, LabelEntity entity) {
        // todo
        return entity;
    }
}
