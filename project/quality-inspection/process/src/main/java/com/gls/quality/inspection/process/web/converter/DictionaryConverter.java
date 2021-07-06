package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.DictionaryEntity;
import com.gls.quality.inspection.process.web.model.DictionaryModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class DictionaryConverter implements BaseConverter<DictionaryEntity, DictionaryModel> {
    @Override
    public DictionaryModel copySourceToTarget(DictionaryEntity entity, DictionaryModel model) {
        return model;
    }

    @Override
    public DictionaryEntity copyTargetToSource(DictionaryModel model, DictionaryEntity entity) {
        return entity;
    }
}
