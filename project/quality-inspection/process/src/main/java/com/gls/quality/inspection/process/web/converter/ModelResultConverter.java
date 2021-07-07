package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultEntity;
import com.gls.quality.inspection.process.web.model.ModelResultModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ModelResultConverter implements BaseConverter<ModelResultEntity, ModelResultModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;

    @Override
    public ModelResultModel copySourceToTarget(ModelResultEntity modelResultEntity, ModelResultModel modelResultModel) {
        modelResultModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(modelResultEntity.getExtractCheckAudio()));
        modelResultModel.setLabel(modelResultEntity.getLabel());
        modelResultModel.setCategory(modelResultEntity.getCategory());
        modelResultModel.setParagraph(modelResultEntity.getParagraph());
        modelResultModel.setSourceType(modelResultEntity.getSourceType());
        modelResultModel.setType(modelResultEntity.getType());
        modelResultModel.setId(modelResultEntity.getId());
        return modelResultModel;
    }

    @Override
    public ModelResultEntity copyTargetToSource(ModelResultModel modelResultModel, ModelResultEntity modelResultEntity) {
        modelResultEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(modelResultModel.getExtractCheckAudio()));
        modelResultEntity.setLabel(modelResultModel.getLabel());
        modelResultEntity.setCategory(modelResultModel.getCategory());
        modelResultEntity.setParagraph(modelResultModel.getParagraph());
        modelResultEntity.setSourceType(modelResultModel.getSourceType());
        modelResultEntity.setType(modelResultModel.getType());
        modelResultEntity.setId(modelResultModel.getId());
        return modelResultEntity;
    }
}