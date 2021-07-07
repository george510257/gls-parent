package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SpotCheckConverter implements BaseConverter<SpotCheckEntity, SpotCheckModel> {
    @Resource
    private ExtractCheckConverter extractCheckConverter;

    @Override
    public SpotCheckModel copySourceToTarget(SpotCheckEntity spotCheckEntity, SpotCheckModel spotCheckModel) {
        spotCheckModel.setSpotCheckName(spotCheckEntity.getSpotCheckName());
        spotCheckModel.setExtractCheck(extractCheckConverter.sourceToTarget(spotCheckEntity.getExtractCheck()));
        spotCheckModel.setDeadline(spotCheckEntity.getDeadline());
        spotCheckModel.setSpotCheckSchedule(spotCheckEntity.getSpotCheckSchedule());
        spotCheckModel.setDialogueNumber(spotCheckEntity.getDialogueNumber());
        spotCheckModel.setIsInvalided(spotCheckEntity.getIsInvalided());
        spotCheckModel.setStatus(spotCheckEntity.getStatus());
        spotCheckModel.setId(spotCheckEntity.getId());
        return spotCheckModel;
    }

    @Override
    public SpotCheckEntity copyTargetToSource(SpotCheckModel spotCheckModel, SpotCheckEntity spotCheckEntity) {
        spotCheckEntity.setSpotCheckName(spotCheckModel.getSpotCheckName());
        spotCheckEntity.setExtractCheck(extractCheckConverter.targetToSource(spotCheckModel.getExtractCheck()));
        spotCheckEntity.setDeadline(spotCheckModel.getDeadline());
        spotCheckEntity.setSpotCheckSchedule(spotCheckModel.getSpotCheckSchedule());
        spotCheckEntity.setDialogueNumber(spotCheckModel.getDialogueNumber());
        spotCheckEntity.setIsInvalided(spotCheckModel.getIsInvalided());
        spotCheckEntity.setStatus(spotCheckModel.getStatus());
        spotCheckEntity.setId(spotCheckModel.getId());
        return spotCheckEntity;
    }
}