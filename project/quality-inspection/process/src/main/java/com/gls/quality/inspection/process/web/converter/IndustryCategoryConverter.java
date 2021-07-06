package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IndustryCategoryEntity;
import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class IndustryCategoryConverter implements BaseConverter<IndustryCategoryEntity, IndustryCategoryModel> {
    @Override
    public IndustryCategoryModel copySourceToTarget(IndustryCategoryEntity entity, IndustryCategoryModel model) {
        // todo
        return model;
    }

    @Override
    public IndustryCategoryEntity copyTargetToSource(IndustryCategoryModel model, IndustryCategoryEntity entity) {
        // todo
        return entity;
    }
}
