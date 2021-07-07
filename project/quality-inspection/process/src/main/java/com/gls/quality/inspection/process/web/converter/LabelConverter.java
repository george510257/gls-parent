package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LabelEntity;
import com.gls.quality.inspection.process.web.model.LabelModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class LabelConverter implements BaseConverter<LabelEntity, LabelModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public LabelModel copySourceToTarget(LabelEntity labelEntity, LabelModel labelModel) {
        labelModel.setModel(modelConverter.sourceToTarget(labelEntity.getModel()));
        labelModel.setSemanticCategory(labelEntity.getSemanticCategory());
        labelModel.setSemanticLabel(labelEntity.getSemanticLabel());
        labelModel.setSemanticRole(labelEntity.getSemanticRole());
        labelModel.setStatus(labelEntity.getStatus());
        labelModel.setExtQuestion(labelEntity.getExtQuestion());
        labelModel.setRecommendExtQuestion(labelEntity.getRecommendExtQuestion());
        labelModel.setRule(labelEntity.getRule());
        labelModel.setId(labelEntity.getId());
        return labelModel;
    }

    @Override
    public LabelEntity copyTargetToSource(LabelModel labelModel, LabelEntity labelEntity) {
        labelEntity.setModel(modelConverter.targetToSource(labelModel.getModel()));
        labelEntity.setSemanticCategory(labelModel.getSemanticCategory());
        labelEntity.setSemanticLabel(labelModel.getSemanticLabel());
        labelEntity.setSemanticRole(labelModel.getSemanticRole());
        labelEntity.setStatus(labelModel.getStatus());
        labelEntity.setExtQuestion(labelModel.getExtQuestion());
        labelEntity.setRecommendExtQuestion(labelModel.getRecommendExtQuestion());
        labelEntity.setRule(labelModel.getRule());
        labelEntity.setId(labelModel.getId());
        return labelEntity;
    }
}