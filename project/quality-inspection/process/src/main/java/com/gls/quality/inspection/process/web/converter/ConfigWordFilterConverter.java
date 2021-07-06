package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigWordFilterEntity;
import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigWordFilterConverter implements BaseConverter<ConfigWordFilterEntity, ConfigWordFilterModel> {
    @Override
    public ConfigWordFilterModel copySourceToTarget(ConfigWordFilterEntity entity, ConfigWordFilterModel model) {
        return model;
    }

    @Override
    public ConfigWordFilterEntity copyTargetToSource(ConfigWordFilterModel model, ConfigWordFilterEntity entity) {
        return entity;
    }
}
