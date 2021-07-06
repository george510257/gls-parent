package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.RecommendExtQuestionEntity;
import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class RecommendExtQuestionConverter implements BaseConverter<RecommendExtQuestionEntity, RecommendExtQuestionModel> {
    @Override
    public RecommendExtQuestionModel copySourceToTarget(RecommendExtQuestionEntity entity, RecommendExtQuestionModel model) {
        // todo
        return model;
    }

    @Override
    public RecommendExtQuestionEntity copyTargetToSource(RecommendExtQuestionModel model, RecommendExtQuestionEntity entity) {
        // todo
        return entity;
    }
}
