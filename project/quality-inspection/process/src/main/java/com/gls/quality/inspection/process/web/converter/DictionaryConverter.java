package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.DictionaryEntity;
import com.gls.quality.inspection.process.web.model.DictionaryModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class DictionaryConverter implements BaseConverter<DictionaryEntity, DictionaryModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public DictionaryModel copySourceToTarget(DictionaryEntity dictionaryEntity, DictionaryModel dictionaryModel) {
        dictionaryModel.setModel(modelConverter.sourceToTarget(dictionaryEntity.getModel()));
        dictionaryModel.setType(dictionaryEntity.getType());
        dictionaryModel.setDictContent(dictionaryEntity.getDictContent());
        dictionaryModel.setId(dictionaryEntity.getId());
        return dictionaryModel;
    }

    @Override
    public DictionaryEntity copyTargetToSource(DictionaryModel dictionaryModel, DictionaryEntity dictionaryEntity) {
        dictionaryEntity.setModel(modelConverter.targetToSource(dictionaryModel.getModel()));
        dictionaryEntity.setType(dictionaryModel.getType());
        dictionaryEntity.setDictContent(dictionaryModel.getDictContent());
        dictionaryEntity.setId(dictionaryModel.getId());
        return dictionaryEntity;
    }
}