package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultTextEntity;
import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ModelResultTextConverter implements BaseConverter<ModelResultTextEntity, ModelResultTextModel> {
    @Resource
    private ModelResultConverter modelResultConverter;
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;
    @Resource
    private ExtractCheckAudioTextConverter extractCheckAudioTextConverter;

    @Override
    public ModelResultTextModel copySourceToTarget(ModelResultTextEntity modelResultTextEntity, ModelResultTextModel modelResultTextModel) {
        modelResultTextModel.setModelResult(modelResultConverter.sourceToTarget(modelResultTextEntity.getModelResult()));
        modelResultTextModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(modelResultTextEntity.getExtractCheckAudio()));
        modelResultTextModel.setExtractCheckAudioText(extractCheckAudioTextConverter.sourceToTarget(modelResultTextEntity.getExtractCheckAudioText()));
        modelResultTextModel.setInfo(modelResultTextEntity.getInfo());
        modelResultTextModel.setId(modelResultTextEntity.getId());
        return modelResultTextModel;
    }

    @Override
    public ModelResultTextEntity copyTargetToSource(ModelResultTextModel modelResultTextModel, ModelResultTextEntity modelResultTextEntity) {
        modelResultTextEntity.setModelResult(modelResultConverter.targetToSource(modelResultTextModel.getModelResult()));
        modelResultTextEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(modelResultTextModel.getExtractCheckAudio()));
        modelResultTextEntity.setExtractCheckAudioText(extractCheckAudioTextConverter.targetToSource(modelResultTextModel.getExtractCheckAudioText()));
        modelResultTextEntity.setInfo(modelResultTextModel.getInfo());
        modelResultTextEntity.setId(modelResultTextModel.getId());
        return modelResultTextEntity;
    }
}