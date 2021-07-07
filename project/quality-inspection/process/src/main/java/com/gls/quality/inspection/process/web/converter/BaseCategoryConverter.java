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
    public BaseCategoryModel copySourceToTarget(BaseCategoryEntity baseCategoryEntity, BaseCategoryModel baseCategoryModel) {
        baseCategoryModel.setId(baseCategoryEntity.getId());
        return baseCategoryModel;
    }

    @Override
    public BaseCategoryEntity copyTargetToSource(BaseCategoryModel baseCategoryModel, BaseCategoryEntity baseCategoryEntity) {
        baseCategoryEntity.setId(baseCategoryModel.getId());
        return baseCategoryEntity;
    }
}