package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioConverter implements BaseConverter<ExtractCheckAudioEntity, ExtractCheckAudioModel> {
    @Resource
    private ExtractCheckConverter extractCheckConverter;

    @Override
    public ExtractCheckAudioModel copySourceToTarget(ExtractCheckAudioEntity extractCheckAudioEntity, ExtractCheckAudioModel extractCheckAudioModel) {
        extractCheckAudioModel.setCustomerServiceId(extractCheckAudioEntity.getCustomerServiceId());
        extractCheckAudioModel.setExtractCheck(extractCheckConverter.sourceToTarget(extractCheckAudioEntity.getExtractCheck()));
        extractCheckAudioModel.setDialogueId(extractCheckAudioEntity.getDialogueId());
        extractCheckAudioModel.setStatus(extractCheckAudioEntity.getStatus());
        extractCheckAudioModel.setAudioUrl(extractCheckAudioEntity.getAudioUrl());
        extractCheckAudioModel.setDuration(extractCheckAudioEntity.getDuration());
        extractCheckAudioModel.setDialogueNumber(extractCheckAudioEntity.getDialogueNumber());
        extractCheckAudioModel.setResourceType(extractCheckAudioEntity.getResourceType());
        extractCheckAudioModel.setIsTranslated(extractCheckAudioEntity.getIsTranslated());
        extractCheckAudioModel.setIsLooked(extractCheckAudioEntity.getIsLooked());
        extractCheckAudioModel.setServiceCheckRate(extractCheckAudioEntity.getServiceCheckRate());
        extractCheckAudioModel.setUserCheckRate(extractCheckAudioEntity.getUserCheckRate());
        extractCheckAudioModel.setViolationCount(extractCheckAudioEntity.getViolationCount());
        extractCheckAudioModel.setRecheckScore(extractCheckAudioEntity.getRecheckScore());
        extractCheckAudioModel.setCheckScore(extractCheckAudioEntity.getCheckScore());
        extractCheckAudioModel.setSpotCheckScore(extractCheckAudioEntity.getSpotCheckScore());
        extractCheckAudioModel.setIsChecked(extractCheckAudioEntity.getIsChecked());
        extractCheckAudioModel.setIsSpotChecked(extractCheckAudioEntity.getIsSpotChecked());
        extractCheckAudioModel.setCheckTime(extractCheckAudioEntity.getCheckTime());
        extractCheckAudioModel.setCustomerMobile(extractCheckAudioEntity.getCustomerMobile());
        extractCheckAudioModel.setIsInvalid(extractCheckAudioEntity.getIsInvalid());
        extractCheckAudioModel.setRecheckUserId(extractCheckAudioEntity.getRecheckUserId());
        extractCheckAudioModel.setApplyCode(extractCheckAudioEntity.getApplyCode());
        extractCheckAudioModel.setDistributeUserId(extractCheckAudioEntity.getDistributeUserId());
        extractCheckAudioModel.setId(extractCheckAudioEntity.getId());
        return extractCheckAudioModel;
    }

    @Override
    public ExtractCheckAudioEntity copyTargetToSource(ExtractCheckAudioModel extractCheckAudioModel, ExtractCheckAudioEntity extractCheckAudioEntity) {
        extractCheckAudioEntity.setCustomerServiceId(extractCheckAudioModel.getCustomerServiceId());
        extractCheckAudioEntity.setExtractCheck(extractCheckConverter.targetToSource(extractCheckAudioModel.getExtractCheck()));
        extractCheckAudioEntity.setDialogueId(extractCheckAudioModel.getDialogueId());
        extractCheckAudioEntity.setStatus(extractCheckAudioModel.getStatus());
        extractCheckAudioEntity.setAudioUrl(extractCheckAudioModel.getAudioUrl());
        extractCheckAudioEntity.setDuration(extractCheckAudioModel.getDuration());
        extractCheckAudioEntity.setDialogueNumber(extractCheckAudioModel.getDialogueNumber());
        extractCheckAudioEntity.setResourceType(extractCheckAudioModel.getResourceType());
        extractCheckAudioEntity.setIsTranslated(extractCheckAudioModel.getIsTranslated());
        extractCheckAudioEntity.setIsLooked(extractCheckAudioModel.getIsLooked());
        extractCheckAudioEntity.setServiceCheckRate(extractCheckAudioModel.getServiceCheckRate());
        extractCheckAudioEntity.setUserCheckRate(extractCheckAudioModel.getUserCheckRate());
        extractCheckAudioEntity.setViolationCount(extractCheckAudioModel.getViolationCount());
        extractCheckAudioEntity.setRecheckScore(extractCheckAudioModel.getRecheckScore());
        extractCheckAudioEntity.setCheckScore(extractCheckAudioModel.getCheckScore());
        extractCheckAudioEntity.setSpotCheckScore(extractCheckAudioModel.getSpotCheckScore());
        extractCheckAudioEntity.setIsChecked(extractCheckAudioModel.getIsChecked());
        extractCheckAudioEntity.setIsSpotChecked(extractCheckAudioModel.getIsSpotChecked());
        extractCheckAudioEntity.setCheckTime(extractCheckAudioModel.getCheckTime());
        extractCheckAudioEntity.setCustomerMobile(extractCheckAudioModel.getCustomerMobile());
        extractCheckAudioEntity.setIsInvalid(extractCheckAudioModel.getIsInvalid());
        extractCheckAudioEntity.setRecheckUserId(extractCheckAudioModel.getRecheckUserId());
        extractCheckAudioEntity.setApplyCode(extractCheckAudioModel.getApplyCode());
        extractCheckAudioEntity.setDistributeUserId(extractCheckAudioModel.getDistributeUserId());
        extractCheckAudioEntity.setId(extractCheckAudioModel.getId());
        return extractCheckAudioEntity;
    }
}