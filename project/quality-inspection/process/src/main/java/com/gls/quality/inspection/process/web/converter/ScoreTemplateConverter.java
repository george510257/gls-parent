package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ScoreTemplateEntity;
import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ScoreTemplateConverter implements BaseConverter<ScoreTemplateEntity, ScoreTemplateModel> {
    @Override
    public ScoreTemplateModel copySourceToTarget(ScoreTemplateEntity entity, ScoreTemplateModel model) {
        // todo
        return model;
    }

    @Override
    public ScoreTemplateEntity copyTargetToSource(ScoreTemplateModel model, ScoreTemplateEntity entity) {
        // todo
        return entity;
    }
}
