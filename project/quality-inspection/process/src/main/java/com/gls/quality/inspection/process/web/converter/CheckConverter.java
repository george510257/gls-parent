package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CheckEntity;
import com.gls.quality.inspection.process.web.model.CheckModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CheckConverter implements BaseConverter<CheckEntity, CheckModel> {
    @Override
    public CheckModel copySourceToTarget(CheckEntity entity, CheckModel model) {
        // todo
        return model;
    }

    @Override
    public CheckEntity copyTargetToSource(CheckModel model, CheckEntity entity) {
        // todo
        return entity;
    }
}
