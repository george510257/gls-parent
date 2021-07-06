package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CombinedScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CombinedScoreItemsConverter implements BaseConverter<CombinedScoreItemsEntity, CombinedScoreItemsModel> {
    @Override
    public CombinedScoreItemsModel copySourceToTarget(CombinedScoreItemsEntity entity, CombinedScoreItemsModel model) {
        // todo
        return model;
    }

    @Override
    public CombinedScoreItemsEntity copyTargetToSource(CombinedScoreItemsModel model, CombinedScoreItemsEntity entity) {
        // todo
        return entity;
    }
}
