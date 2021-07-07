package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ExtractCheckConverter implements BaseConverter<ExtractCheckEntity, ExtractCheckModel> {
    @Resource
    private UserConverter userConverter;
    @Resource
    private ScoreTemplateConverter scoreTemplateConverter;
    @Resource
    private IndustryCategoryConverter industryCategoryConverter;
    @Resource
    private ModelConverter modelConverter;

    @Override
    public ExtractCheckModel copySourceToTarget(ExtractCheckEntity extractCheckEntity, ExtractCheckModel extractCheckModel) {
        extractCheckModel.setUser(userConverter.sourceToTarget(extractCheckEntity.getUser()));
        extractCheckModel.setExtractCheckName(extractCheckEntity.getExtractCheckName());
        extractCheckModel.setDifferentiateRole(extractCheckEntity.getDifferentiateRole());
        extractCheckModel.setScoreTemplate(scoreTemplateConverter.sourceToTarget(extractCheckEntity.getScoreTemplate()));
        extractCheckModel.setIndustryCategory(industryCategoryConverter.sourceToTarget(extractCheckEntity.getIndustryCategory()));
        extractCheckModel.setTotalDuration(extractCheckEntity.getTotalDuration());
        extractCheckModel.setModelName(extractCheckEntity.getModelName());
        extractCheckModel.setDeadline(extractCheckEntity.getDeadline());
        extractCheckModel.setStatus(extractCheckEntity.getStatus());
        extractCheckModel.setExtractCheckSchedule(extractCheckEntity.getExtractCheckSchedule());
        extractCheckModel.setModel(modelConverter.sourceToTarget(extractCheckEntity.getModel()));
        extractCheckModel.setFileUrl(extractCheckEntity.getFileUrl());
        extractCheckModel.setResourceType(extractCheckEntity.getResourceType());
        extractCheckModel.setIsChecked(extractCheckEntity.getIsChecked());
        extractCheckModel.setIsFinished(extractCheckEntity.getIsFinished());
        extractCheckModel.setExtractCheckType(extractCheckEntity.getExtractCheckType());
        extractCheckModel.setStartTime(extractCheckEntity.getStartTime());
        extractCheckModel.setBaseScore(extractCheckEntity.getBaseScore());
        extractCheckModel.setEndTime(extractCheckEntity.getEndTime());
        extractCheckModel.setId(extractCheckEntity.getId());
        return extractCheckModel;
    }

    @Override
    public ExtractCheckEntity copyTargetToSource(ExtractCheckModel extractCheckModel, ExtractCheckEntity extractCheckEntity) {
        extractCheckEntity.setUser(userConverter.targetToSource(extractCheckModel.getUser()));
        extractCheckEntity.setExtractCheckName(extractCheckModel.getExtractCheckName());
        extractCheckEntity.setDifferentiateRole(extractCheckModel.getDifferentiateRole());
        extractCheckEntity.setScoreTemplate(scoreTemplateConverter.targetToSource(extractCheckModel.getScoreTemplate()));
        extractCheckEntity.setIndustryCategory(industryCategoryConverter.targetToSource(extractCheckModel.getIndustryCategory()));
        extractCheckEntity.setTotalDuration(extractCheckModel.getTotalDuration());
        extractCheckEntity.setModelName(extractCheckModel.getModelName());
        extractCheckEntity.setDeadline(extractCheckModel.getDeadline());
        extractCheckEntity.setStatus(extractCheckModel.getStatus());
        extractCheckEntity.setExtractCheckSchedule(extractCheckModel.getExtractCheckSchedule());
        extractCheckEntity.setModel(modelConverter.targetToSource(extractCheckModel.getModel()));
        extractCheckEntity.setFileUrl(extractCheckModel.getFileUrl());
        extractCheckEntity.setResourceType(extractCheckModel.getResourceType());
        extractCheckEntity.setIsChecked(extractCheckModel.getIsChecked());
        extractCheckEntity.setIsFinished(extractCheckModel.getIsFinished());
        extractCheckEntity.setExtractCheckType(extractCheckModel.getExtractCheckType());
        extractCheckEntity.setStartTime(extractCheckModel.getStartTime());
        extractCheckEntity.setBaseScore(extractCheckModel.getBaseScore());
        extractCheckEntity.setEndTime(extractCheckModel.getEndTime());
        extractCheckEntity.setId(extractCheckModel.getId());
        return extractCheckEntity;
    }
}