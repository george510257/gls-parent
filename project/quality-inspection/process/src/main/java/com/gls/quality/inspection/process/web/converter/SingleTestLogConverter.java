package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SingleTestLogEntity;
import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SingleTestLogConverter implements BaseConverter<SingleTestLogEntity, SingleTestLogModel> {
    @Override
    public SingleTestLogModel copySourceToTarget(SingleTestLogEntity entity, SingleTestLogModel model) {
        // todo
        return model;
    }

    @Override
    public SingleTestLogEntity copyTargetToSource(SingleTestLogModel model, SingleTestLogEntity entity) {
        // todo
        return entity;
    }
}
