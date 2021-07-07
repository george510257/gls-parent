package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IntentionAnalysisEntity;
import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class IntentionAnalysisConverter implements BaseConverter<IntentionAnalysisEntity, IntentionAnalysisModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public IntentionAnalysisModel copySourceToTarget(IntentionAnalysisEntity intentionAnalysisEntity, IntentionAnalysisModel intentionAnalysisModel) {
        intentionAnalysisModel.setModel(modelConverter.sourceToTarget(intentionAnalysisEntity.getModel()));
        intentionAnalysisModel.setTotalIntention(intentionAnalysisEntity.getTotalIntention());
        intentionAnalysisModel.setContent(intentionAnalysisEntity.getContent());
        intentionAnalysisModel.setLabel(intentionAnalysisEntity.getLabel());
        intentionAnalysisModel.setContentDetail(intentionAnalysisEntity.getContentDetail());
        intentionAnalysisModel.setStatus(intentionAnalysisEntity.getStatus());
        intentionAnalysisModel.setId(intentionAnalysisEntity.getId());
        return intentionAnalysisModel;
    }

    @Override
    public IntentionAnalysisEntity copyTargetToSource(IntentionAnalysisModel intentionAnalysisModel, IntentionAnalysisEntity intentionAnalysisEntity) {
        intentionAnalysisEntity.setModel(modelConverter.targetToSource(intentionAnalysisModel.getModel()));
        intentionAnalysisEntity.setTotalIntention(intentionAnalysisModel.getTotalIntention());
        intentionAnalysisEntity.setContent(intentionAnalysisModel.getContent());
        intentionAnalysisEntity.setLabel(intentionAnalysisModel.getLabel());
        intentionAnalysisEntity.setContentDetail(intentionAnalysisModel.getContentDetail());
        intentionAnalysisEntity.setStatus(intentionAnalysisModel.getStatus());
        intentionAnalysisEntity.setId(intentionAnalysisModel.getId());
        return intentionAnalysisEntity;
    }
}