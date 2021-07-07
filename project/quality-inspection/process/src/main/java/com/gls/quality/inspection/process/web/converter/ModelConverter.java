package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ModelEntity;
import com.gls.quality.inspection.process.web.model.ModelModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ModelConverter implements BaseConverter<ModelEntity, ModelModel> {
    @Resource
    private IndustryCategoryConverter industryCategoryConverter;

    @Override
    public ModelModel copySourceToTarget(ModelEntity modelEntity, ModelModel modelModel) {
        modelModel.setIndustryCategory(industryCategoryConverter.sourceToTarget(modelEntity.getIndustryCategory()));
        modelModel.setStatus(modelEntity.getStatus());
        modelModel.setReleased(modelEntity.getReleased());
        modelModel.setCreateBy(modelEntity.getCreateBy());
        modelModel.setVoiceTimeout(modelEntity.getVoiceTimeout());
        modelModel.setVoiceShutdown(modelEntity.getVoiceShutdown());
        modelModel.setVoiceFast(modelEntity.getVoiceFast());
        modelModel.setVoiceDb(modelEntity.getVoiceDb());
        modelModel.setVoiceInterrupt(modelEntity.getVoiceInterrupt());
        modelModel.setVoiceInterruptStatus(modelEntity.getVoiceInterruptStatus());
        modelModel.setLabelScore(modelEntity.getLabelScore());
        modelModel.setTokenModule(modelEntity.getTokenModule());
        modelModel.setSemanticsModule(modelEntity.getSemanticsModule());
        modelModel.setId(modelEntity.getId());
        return modelModel;
    }

    @Override
    public ModelEntity copyTargetToSource(ModelModel modelModel, ModelEntity modelEntity) {
        modelEntity.setIndustryCategory(industryCategoryConverter.targetToSource(modelModel.getIndustryCategory()));
        modelEntity.setStatus(modelModel.getStatus());
        modelEntity.setReleased(modelModel.getReleased());
        modelEntity.setCreateBy(modelModel.getCreateBy());
        modelEntity.setVoiceTimeout(modelModel.getVoiceTimeout());
        modelEntity.setVoiceShutdown(modelModel.getVoiceShutdown());
        modelEntity.setVoiceFast(modelModel.getVoiceFast());
        modelEntity.setVoiceDb(modelModel.getVoiceDb());
        modelEntity.setVoiceInterrupt(modelModel.getVoiceInterrupt());
        modelEntity.setVoiceInterruptStatus(modelModel.getVoiceInterruptStatus());
        modelEntity.setLabelScore(modelModel.getLabelScore());
        modelEntity.setTokenModule(modelModel.getTokenModule());
        modelEntity.setSemanticsModule(modelModel.getSemanticsModule());
        modelEntity.setId(modelModel.getId());
        return modelEntity;
    }
}