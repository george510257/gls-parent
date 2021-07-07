package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ExtractCheckResultConverter implements BaseConverter<ExtractCheckResultEntity, ExtractCheckResultModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;
    @Resource
    private ScoreItemsConverter scoreItemsConverter;

    @Override
    public ExtractCheckResultModel copySourceToTarget(ExtractCheckResultEntity extractCheckResultEntity, ExtractCheckResultModel extractCheckResultModel) {
        extractCheckResultModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(extractCheckResultEntity.getExtractCheckAudio()));
        extractCheckResultModel.setViolationsItem(extractCheckResultEntity.getViolationsItem());
        extractCheckResultModel.setViolationsScore(extractCheckResultEntity.getViolationsScore());
        extractCheckResultModel.setCategory(extractCheckResultEntity.getCategory());
        extractCheckResultModel.setParagraph(extractCheckResultEntity.getParagraph());
        extractCheckResultModel.setScoreItems(scoreItemsConverter.sourceToTarget(extractCheckResultEntity.getScoreItems()));
        extractCheckResultModel.setScoreItemType(extractCheckResultEntity.getScoreItemType());
        extractCheckResultModel.setScoreAttribute(extractCheckResultEntity.getScoreAttribute());
        extractCheckResultModel.setInspectionObject(extractCheckResultEntity.getInspectionObject());
        extractCheckResultModel.setKeyInfo(extractCheckResultEntity.getKeyInfo());
        extractCheckResultModel.setType(extractCheckResultEntity.getType());
        extractCheckResultModel.setRecheckStatus(extractCheckResultEntity.getRecheckStatus());
        extractCheckResultModel.setRepresentation(extractCheckResultEntity.getRepresentation());
        extractCheckResultModel.setIsMissed(extractCheckResultEntity.getIsMissed());
        extractCheckResultModel.setIsError(extractCheckResultEntity.getIsError());
        extractCheckResultModel.setScoreStrategy(extractCheckResultEntity.getScoreStrategy());
        extractCheckResultModel.setId(extractCheckResultEntity.getId());
        return extractCheckResultModel;
    }

    @Override
    public ExtractCheckResultEntity copyTargetToSource(ExtractCheckResultModel extractCheckResultModel, ExtractCheckResultEntity extractCheckResultEntity) {
        extractCheckResultEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(extractCheckResultModel.getExtractCheckAudio()));
        extractCheckResultEntity.setViolationsItem(extractCheckResultModel.getViolationsItem());
        extractCheckResultEntity.setViolationsScore(extractCheckResultModel.getViolationsScore());
        extractCheckResultEntity.setCategory(extractCheckResultModel.getCategory());
        extractCheckResultEntity.setParagraph(extractCheckResultModel.getParagraph());
        extractCheckResultEntity.setScoreItems(scoreItemsConverter.targetToSource(extractCheckResultModel.getScoreItems()));
        extractCheckResultEntity.setScoreItemType(extractCheckResultModel.getScoreItemType());
        extractCheckResultEntity.setScoreAttribute(extractCheckResultModel.getScoreAttribute());
        extractCheckResultEntity.setInspectionObject(extractCheckResultModel.getInspectionObject());
        extractCheckResultEntity.setKeyInfo(extractCheckResultModel.getKeyInfo());
        extractCheckResultEntity.setType(extractCheckResultModel.getType());
        extractCheckResultEntity.setRecheckStatus(extractCheckResultModel.getRecheckStatus());
        extractCheckResultEntity.setRepresentation(extractCheckResultModel.getRepresentation());
        extractCheckResultEntity.setIsMissed(extractCheckResultModel.getIsMissed());
        extractCheckResultEntity.setIsError(extractCheckResultModel.getIsError());
        extractCheckResultEntity.setScoreStrategy(extractCheckResultModel.getScoreStrategy());
        extractCheckResultEntity.setId(extractCheckResultModel.getId());
        return extractCheckResultEntity;
    }
}