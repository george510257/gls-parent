package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestLogEntity;
import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class TotalTestLogConverter implements BaseConverter<TotalTestLogEntity, TotalTestLogModel> {
    @Override
    public TotalTestLogModel copySourceToTarget(TotalTestLogEntity entity, TotalTestLogModel model) {
        return model;
    }

    @Override
    public TotalTestLogEntity copyTargetToSource(TotalTestLogModel model, TotalTestLogEntity entity) {
        return entity;
    }
}
