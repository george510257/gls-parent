package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioTextConverter implements BaseConverter<ExtractCheckAudioTextEntity, ExtractCheckAudioTextModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;

    @Override
    public ExtractCheckAudioTextModel copySourceToTarget(ExtractCheckAudioTextEntity extractCheckAudioTextEntity, ExtractCheckAudioTextModel extractCheckAudioTextModel) {
        extractCheckAudioTextModel.setContent(extractCheckAudioTextEntity.getContent());
        extractCheckAudioTextModel.setContentCorrect(extractCheckAudioTextEntity.getContentCorrect());
        extractCheckAudioTextModel.setCheckRate(extractCheckAudioTextEntity.getCheckRate());
        extractCheckAudioTextModel.setSpk(extractCheckAudioTextEntity.getSpk());
        extractCheckAudioTextModel.setRole(extractCheckAudioTextEntity.getRole());
        extractCheckAudioTextModel.setBegin(extractCheckAudioTextEntity.getBegin());
        extractCheckAudioTextModel.setEnd(extractCheckAudioTextEntity.getEnd());
        extractCheckAudioTextModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(extractCheckAudioTextEntity.getExtractCheckAudio()));
        extractCheckAudioTextModel.setExcelTime(extractCheckAudioTextEntity.getExcelTime());
        extractCheckAudioTextModel.setId(extractCheckAudioTextEntity.getId());
        return extractCheckAudioTextModel;
    }

    @Override
    public ExtractCheckAudioTextEntity copyTargetToSource(ExtractCheckAudioTextModel extractCheckAudioTextModel, ExtractCheckAudioTextEntity extractCheckAudioTextEntity) {
        extractCheckAudioTextEntity.setContent(extractCheckAudioTextModel.getContent());
        extractCheckAudioTextEntity.setContentCorrect(extractCheckAudioTextModel.getContentCorrect());
        extractCheckAudioTextEntity.setCheckRate(extractCheckAudioTextModel.getCheckRate());
        extractCheckAudioTextEntity.setSpk(extractCheckAudioTextModel.getSpk());
        extractCheckAudioTextEntity.setRole(extractCheckAudioTextModel.getRole());
        extractCheckAudioTextEntity.setBegin(extractCheckAudioTextModel.getBegin());
        extractCheckAudioTextEntity.setEnd(extractCheckAudioTextModel.getEnd());
        extractCheckAudioTextEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(extractCheckAudioTextModel.getExtractCheckAudio()));
        extractCheckAudioTextEntity.setExcelTime(extractCheckAudioTextModel.getExcelTime());
        extractCheckAudioTextEntity.setId(extractCheckAudioTextModel.getId());
        return extractCheckAudioTextEntity;
    }
}