package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.BaseCategoryEntity;
import com.gls.quality.inspection.process.web.model.BaseCategoryModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class BaseCategoryConverter implements BaseConverter<BaseCategoryEntity, BaseCategoryModel> {
    @Override
    public BaseCategoryModel copySourceToTarget(BaseCategoryEntity entity, BaseCategoryModel model) {
        return model;
    }

    @Override
    public BaseCategoryEntity copyTargetToSource(BaseCategoryModel model, BaseCategoryEntity entity) {
        return entity;
    }
}
