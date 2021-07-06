package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SpotCheckConverter implements BaseConverter<SpotCheckEntity, SpotCheckModel> {
    @Override
    public SpotCheckModel copySourceToTarget(SpotCheckEntity entity, SpotCheckModel model) {
        return model;
    }

    @Override
    public SpotCheckEntity copyTargetToSource(SpotCheckModel model, SpotCheckEntity entity) {
        return entity;
    }
}
