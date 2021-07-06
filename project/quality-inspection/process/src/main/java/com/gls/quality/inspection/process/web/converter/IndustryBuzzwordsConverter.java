package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IndustryBuzzwordsEntity;
import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class IndustryBuzzwordsConverter implements BaseConverter<IndustryBuzzwordsEntity, IndustryBuzzwordsModel> {
    @Override
    public IndustryBuzzwordsModel copySourceToTarget(IndustryBuzzwordsEntity entity, IndustryBuzzwordsModel model) {
        return model;
    }

    @Override
    public IndustryBuzzwordsEntity copyTargetToSource(IndustryBuzzwordsModel model, IndustryBuzzwordsEntity entity) {
        return entity;
    }
}
