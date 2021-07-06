package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigScoreSystemEntity;
import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigScoreSystemConverter implements BaseConverter<ConfigScoreSystemEntity, ConfigScoreSystemModel> {
    @Override
    public ConfigScoreSystemModel copySourceToTarget(ConfigScoreSystemEntity entity, ConfigScoreSystemModel model) {
        // todo
        return model;
    }

    @Override
    public ConfigScoreSystemEntity copyTargetToSource(ConfigScoreSystemModel model, ConfigScoreSystemEntity entity) {
        // todo
        return entity;
    }
}
