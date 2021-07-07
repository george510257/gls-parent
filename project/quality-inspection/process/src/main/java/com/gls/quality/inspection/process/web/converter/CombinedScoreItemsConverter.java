package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CombinedScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class CombinedScoreItemsConverter implements BaseConverter<CombinedScoreItemsEntity, CombinedScoreItemsModel> {
    @Resource
    private ScoreTemplateConverter scoreTemplateConverter;

    @Override
    public CombinedScoreItemsModel copySourceToTarget(CombinedScoreItemsEntity combinedScoreItemsEntity, CombinedScoreItemsModel combinedScoreItemsModel) {
        combinedScoreItemsModel.setScoreTemplate(scoreTemplateConverter.sourceToTarget(combinedScoreItemsEntity.getScoreTemplate()));
        combinedScoreItemsModel.setScoreItemsIds(combinedScoreItemsEntity.getScoreItemsIds());
        combinedScoreItemsModel.setDisplayName(combinedScoreItemsEntity.getDisplayName());
        combinedScoreItemsModel.setScoreItemsNames(combinedScoreItemsEntity.getScoreItemsNames());
        combinedScoreItemsModel.setScoreStrategy(combinedScoreItemsEntity.getScoreStrategy());
        combinedScoreItemsModel.setInspectionObject(combinedScoreItemsEntity.getInspectionObject());
        combinedScoreItemsModel.setScoreAttribute(combinedScoreItemsEntity.getScoreAttribute());
        combinedScoreItemsModel.setScore(combinedScoreItemsEntity.getScore());
        combinedScoreItemsModel.setId(combinedScoreItemsEntity.getId());
        return combinedScoreItemsModel;
    }

    @Override
    public CombinedScoreItemsEntity copyTargetToSource(CombinedScoreItemsModel combinedScoreItemsModel, CombinedScoreItemsEntity combinedScoreItemsEntity) {
        combinedScoreItemsEntity.setScoreTemplate(scoreTemplateConverter.targetToSource(combinedScoreItemsModel.getScoreTemplate()));
        combinedScoreItemsEntity.setScoreItemsIds(combinedScoreItemsModel.getScoreItemsIds());
        combinedScoreItemsEntity.setDisplayName(combinedScoreItemsModel.getDisplayName());
        combinedScoreItemsEntity.setScoreItemsNames(combinedScoreItemsModel.getScoreItemsNames());
        combinedScoreItemsEntity.setScoreStrategy(combinedScoreItemsModel.getScoreStrategy());
        combinedScoreItemsEntity.setInspectionObject(combinedScoreItemsModel.getInspectionObject());
        combinedScoreItemsEntity.setScoreAttribute(combinedScoreItemsModel.getScoreAttribute());
        combinedScoreItemsEntity.setScore(combinedScoreItemsModel.getScore());
        combinedScoreItemsEntity.setId(combinedScoreItemsModel.getId());
        return combinedScoreItemsEntity;
    }
}