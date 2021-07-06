package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ScoreItemsConverter implements BaseConverter<ScoreItemsEntity, ScoreItemsModel> {
    @Override
    public ScoreItemsModel copySourceToTarget(ScoreItemsEntity entity, ScoreItemsModel model) {
        return model;
    }

    @Override
    public ScoreItemsEntity copyTargetToSource(ScoreItemsModel model, ScoreItemsEntity entity) {
        return entity;
    }
}
