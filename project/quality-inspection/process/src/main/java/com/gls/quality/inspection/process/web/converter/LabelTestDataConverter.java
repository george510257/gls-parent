package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LabelTestDataEntity;
import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class LabelTestDataConverter implements BaseConverter<LabelTestDataEntity, LabelTestDataModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public LabelTestDataModel copySourceToTarget(LabelTestDataEntity labelTestDataEntity, LabelTestDataModel labelTestDataModel) {
        labelTestDataModel.setModel(modelConverter.sourceToTarget(labelTestDataEntity.getModel()));
        labelTestDataModel.setStatus(labelTestDataEntity.getStatus());
        labelTestDataModel.setTotalLabelPassingRate(labelTestDataEntity.getTotalLabelPassingRate());
        labelTestDataModel.setVoiceCheckPassingRate(labelTestDataEntity.getVoiceCheckPassingRate());
        labelTestDataModel.setSemanticLabelPassingRate(labelTestDataEntity.getSemanticLabelPassingRate());
        labelTestDataModel.setComplexLabelPassingRate(labelTestDataEntity.getComplexLabelPassingRate());
        labelTestDataModel.setId(labelTestDataEntity.getId());
        return labelTestDataModel;
    }

    @Override
    public LabelTestDataEntity copyTargetToSource(LabelTestDataModel labelTestDataModel, LabelTestDataEntity labelTestDataEntity) {
        labelTestDataEntity.setModel(modelConverter.targetToSource(labelTestDataModel.getModel()));
        labelTestDataEntity.setStatus(labelTestDataModel.getStatus());
        labelTestDataEntity.setTotalLabelPassingRate(labelTestDataModel.getTotalLabelPassingRate());
        labelTestDataEntity.setVoiceCheckPassingRate(labelTestDataModel.getVoiceCheckPassingRate());
        labelTestDataEntity.setSemanticLabelPassingRate(labelTestDataModel.getSemanticLabelPassingRate());
        labelTestDataEntity.setComplexLabelPassingRate(labelTestDataModel.getComplexLabelPassingRate());
        labelTestDataEntity.setId(labelTestDataModel.getId());
        return labelTestDataEntity;
    }
}