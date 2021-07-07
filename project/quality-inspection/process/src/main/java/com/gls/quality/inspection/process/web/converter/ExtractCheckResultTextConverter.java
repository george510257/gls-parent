package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ExtractCheckResultTextConverter implements BaseConverter<ExtractCheckResultTextEntity, ExtractCheckResultTextModel> {
    @Resource
    private ExtractCheckResultConverter extractCheckResultConverter;
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;
    @Resource
    private ExtractCheckAudioTextConverter extractCheckAudioTextConverter;

    @Override
    public ExtractCheckResultTextModel copySourceToTarget(ExtractCheckResultTextEntity extractCheckResultTextEntity, ExtractCheckResultTextModel extractCheckResultTextModel) {
        extractCheckResultTextModel.setExtractCheckResult(extractCheckResultConverter.sourceToTarget(extractCheckResultTextEntity.getExtractCheckResult()));
        extractCheckResultTextModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(extractCheckResultTextEntity.getExtractCheckAudio()));
        extractCheckResultTextModel.setExtractCheckAudioText(extractCheckAudioTextConverter.sourceToTarget(extractCheckResultTextEntity.getExtractCheckAudioText()));
        extractCheckResultTextModel.setInfo(extractCheckResultTextEntity.getInfo());
        extractCheckResultTextModel.setViolationsScore(extractCheckResultTextEntity.getViolationsScore());
        extractCheckResultTextModel.setIsError(extractCheckResultTextEntity.getIsError());
        extractCheckResultTextModel.setId(extractCheckResultTextEntity.getId());
        return extractCheckResultTextModel;
    }

    @Override
    public ExtractCheckResultTextEntity copyTargetToSource(ExtractCheckResultTextModel extractCheckResultTextModel, ExtractCheckResultTextEntity extractCheckResultTextEntity) {
        extractCheckResultTextEntity.setExtractCheckResult(extractCheckResultConverter.targetToSource(extractCheckResultTextModel.getExtractCheckResult()));
        extractCheckResultTextEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(extractCheckResultTextModel.getExtractCheckAudio()));
        extractCheckResultTextEntity.setExtractCheckAudioText(extractCheckAudioTextConverter.targetToSource(extractCheckResultTextModel.getExtractCheckAudioText()));
        extractCheckResultTextEntity.setInfo(extractCheckResultTextModel.getInfo());
        extractCheckResultTextEntity.setViolationsScore(extractCheckResultTextModel.getViolationsScore());
        extractCheckResultTextEntity.setIsError(extractCheckResultTextModel.getIsError());
        extractCheckResultTextEntity.setId(extractCheckResultTextModel.getId());
        return extractCheckResultTextEntity;
    }
}