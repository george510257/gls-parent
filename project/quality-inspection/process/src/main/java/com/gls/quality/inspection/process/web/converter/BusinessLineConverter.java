package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.BusinessLineEntity;
import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class BusinessLineConverter implements BaseConverter<BusinessLineEntity, BusinessLineModel> {
    @Override
    public BusinessLineModel copySourceToTarget(BusinessLineEntity entity, BusinessLineModel model) {
        // todo
        return model;
    }

    @Override
    public BusinessLineEntity copyTargetToSource(BusinessLineModel model, BusinessLineEntity entity) {
        // todo
        return entity;
    }
}
