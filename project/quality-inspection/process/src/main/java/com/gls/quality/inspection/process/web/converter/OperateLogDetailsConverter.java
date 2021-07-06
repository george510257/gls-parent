package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.OperateLogDetailsEntity;
import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class OperateLogDetailsConverter implements BaseConverter<OperateLogDetailsEntity, OperateLogDetailsModel> {
    @Override
    public OperateLogDetailsModel copySourceToTarget(OperateLogDetailsEntity entity, OperateLogDetailsModel model) {
        // todo
        return model;
    }

    @Override
    public OperateLogDetailsEntity copyTargetToSource(OperateLogDetailsModel model, OperateLogDetailsEntity entity) {
        // todo
        return entity;
    }
}
