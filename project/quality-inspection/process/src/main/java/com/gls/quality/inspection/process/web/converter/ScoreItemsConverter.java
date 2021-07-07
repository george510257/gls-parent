package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ScoreItemsConverter implements BaseConverter<ScoreItemsEntity, ScoreItemsModel> {
    @Resource
    private ScoreTemplateConverter scoreTemplateConverter;

    @Override
    public ScoreItemsModel copySourceToTarget(ScoreItemsEntity scoreItemsEntity, ScoreItemsModel scoreItemsModel) {
        scoreItemsModel.setScoreTemplate(scoreTemplateConverter.sourceToTarget(scoreItemsEntity.getScoreTemplate()));
        scoreItemsModel.setType(scoreItemsEntity.getType());
        scoreItemsModel.setScoreItemsTitle(scoreItemsEntity.getScoreItemsTitle());
        scoreItemsModel.setScoreStrategy(scoreItemsEntity.getScoreStrategy());
        scoreItemsModel.setInspectionObject(scoreItemsEntity.getInspectionObject());
        scoreItemsModel.setScoreAttribute(scoreItemsEntity.getScoreAttribute());
        scoreItemsModel.setScore(scoreItemsEntity.getScore());
        scoreItemsModel.setScorePrinciple(scoreItemsEntity.getScorePrinciple());
        scoreItemsModel.setId(scoreItemsEntity.getId());
        return scoreItemsModel;
    }

    @Override
    public ScoreItemsEntity copyTargetToSource(ScoreItemsModel scoreItemsModel, ScoreItemsEntity scoreItemsEntity) {
        scoreItemsEntity.setScoreTemplate(scoreTemplateConverter.targetToSource(scoreItemsModel.getScoreTemplate()));
        scoreItemsEntity.setType(scoreItemsModel.getType());
        scoreItemsEntity.setScoreItemsTitle(scoreItemsModel.getScoreItemsTitle());
        scoreItemsEntity.setScoreStrategy(scoreItemsModel.getScoreStrategy());
        scoreItemsEntity.setInspectionObject(scoreItemsModel.getInspectionObject());
        scoreItemsEntity.setScoreAttribute(scoreItemsModel.getScoreAttribute());
        scoreItemsEntity.setScore(scoreItemsModel.getScore());
        scoreItemsEntity.setScorePrinciple(scoreItemsModel.getScorePrinciple());
        scoreItemsEntity.setId(scoreItemsModel.getId());
        return scoreItemsEntity;
    }
}