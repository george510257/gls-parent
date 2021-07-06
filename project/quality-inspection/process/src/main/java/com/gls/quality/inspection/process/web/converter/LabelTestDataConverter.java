package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LabelTestDataEntity;
import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class LabelTestDataConverter implements BaseConverter<LabelTestDataEntity, LabelTestDataModel> {
    @Override
    public LabelTestDataModel copySourceToTarget(LabelTestDataEntity entity, LabelTestDataModel model) {
        // todo
        return model;
    }

    @Override
    public LabelTestDataEntity copyTargetToSource(LabelTestDataModel model, LabelTestDataEntity entity) {
        // todo
        return entity;
    }
}
