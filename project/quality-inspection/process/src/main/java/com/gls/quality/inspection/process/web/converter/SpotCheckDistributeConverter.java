package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckDistributeEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckDistributeModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SpotCheckDistributeConverter implements BaseConverter<SpotCheckDistributeEntity, SpotCheckDistributeModel> {
    @Override
    public SpotCheckDistributeModel copySourceToTarget(SpotCheckDistributeEntity entity, SpotCheckDistributeModel model) {
        // todo
        return model;
    }

    @Override
    public SpotCheckDistributeEntity copyTargetToSource(SpotCheckDistributeModel model, SpotCheckDistributeEntity entity) {
        // todo
        return entity;
    }
}
