package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IntentionAnalysisEntity;
import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class IntentionAnalysisConverter implements BaseConverter<IntentionAnalysisEntity, IntentionAnalysisModel> {
    @Override
    public IntentionAnalysisModel copySourceToTarget(IntentionAnalysisEntity entity, IntentionAnalysisModel model) {
        // todo
        return model;
    }

    @Override
    public IntentionAnalysisEntity copyTargetToSource(IntentionAnalysisModel model, IntentionAnalysisEntity entity) {
        // todo
        return entity;
    }
}
