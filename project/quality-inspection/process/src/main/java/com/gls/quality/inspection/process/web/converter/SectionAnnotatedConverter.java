package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SectionAnnotatedEntity;
import com.gls.quality.inspection.process.web.model.SectionAnnotatedModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SectionAnnotatedConverter implements BaseConverter<SectionAnnotatedEntity, SectionAnnotatedModel> {
    @Override
    public SectionAnnotatedModel copySourceToTarget(SectionAnnotatedEntity entity, SectionAnnotatedModel model) {
        return model;
    }

    @Override
    public SectionAnnotatedEntity copyTargetToSource(SectionAnnotatedModel model, SectionAnnotatedEntity entity) {
        return entity;
    }
}
