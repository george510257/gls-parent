package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestTaskEntity;
import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class TotalTestTaskConverter implements BaseConverter<TotalTestTaskEntity, TotalTestTaskModel> {
    @Override
    public TotalTestTaskModel copySourceToTarget(TotalTestTaskEntity entity, TotalTestTaskModel model) {
        return model;
    }

    @Override
    public TotalTestTaskEntity copyTargetToSource(TotalTestTaskModel model, TotalTestTaskEntity entity) {
        return entity;
    }
}
