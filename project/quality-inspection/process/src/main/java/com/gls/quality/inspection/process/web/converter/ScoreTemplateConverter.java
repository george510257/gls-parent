package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ScoreTemplateEntity;
import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ScoreTemplateConverter implements BaseConverter<ScoreTemplateEntity, ScoreTemplateModel> {
    @Resource
    private ModelConverter modelConverter;
    @Resource
    private UserConverter userConverter;

    @Override
    public ScoreTemplateModel copySourceToTarget(ScoreTemplateEntity scoreTemplateEntity, ScoreTemplateModel scoreTemplateModel) {
        scoreTemplateModel.setTemplateId(scoreTemplateEntity.getTemplateId());
        scoreTemplateModel.setModelName(scoreTemplateEntity.getModelName());
        scoreTemplateModel.setModel(modelConverter.sourceToTarget(scoreTemplateEntity.getModel()));
        scoreTemplateModel.setUser(userConverter.sourceToTarget(scoreTemplateEntity.getUser()));
        scoreTemplateModel.setTemplateName(scoreTemplateEntity.getTemplateName());
        scoreTemplateModel.setBaseScore(scoreTemplateEntity.getBaseScore());
        scoreTemplateModel.setScoreItemsNumber(scoreTemplateEntity.getScoreItemsNumber());
        scoreTemplateModel.setStatus(scoreTemplateEntity.getStatus());
        scoreTemplateModel.setScoreBaseline(scoreTemplateEntity.getScoreBaseline());
        scoreTemplateModel.setId(scoreTemplateEntity.getId());
        return scoreTemplateModel;
    }

    @Override
    public ScoreTemplateEntity copyTargetToSource(ScoreTemplateModel scoreTemplateModel, ScoreTemplateEntity scoreTemplateEntity) {
        scoreTemplateEntity.setTemplateId(scoreTemplateModel.getTemplateId());
        scoreTemplateEntity.setModelName(scoreTemplateModel.getModelName());
        scoreTemplateEntity.setModel(modelConverter.targetToSource(scoreTemplateModel.getModel()));
        scoreTemplateEntity.setUser(userConverter.targetToSource(scoreTemplateModel.getUser()));
        scoreTemplateEntity.setTemplateName(scoreTemplateModel.getTemplateName());
        scoreTemplateEntity.setBaseScore(scoreTemplateModel.getBaseScore());
        scoreTemplateEntity.setScoreItemsNumber(scoreTemplateModel.getScoreItemsNumber());
        scoreTemplateEntity.setStatus(scoreTemplateModel.getStatus());
        scoreTemplateEntity.setScoreBaseline(scoreTemplateModel.getScoreBaseline());
        scoreTemplateEntity.setId(scoreTemplateModel.getId());
        return scoreTemplateEntity;
    }
}