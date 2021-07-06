package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CorpusEntity;
import com.gls.quality.inspection.process.web.model.CorpusModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CorpusConverter implements BaseConverter<CorpusEntity, CorpusModel> {
    @Override
    public CorpusModel copySourceToTarget(CorpusEntity entity, CorpusModel model) {
        return model;
    }

    @Override
    public CorpusEntity copyTargetToSource(CorpusModel model, CorpusEntity entity) {
        return entity;
    }
}
