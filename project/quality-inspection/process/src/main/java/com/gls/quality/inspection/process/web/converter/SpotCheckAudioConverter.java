package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SpotCheckAudioConverter implements BaseConverter<SpotCheckAudioEntity, SpotCheckAudioModel> {
    @Resource
    private SpotCheckConverter spotCheckConverter;
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;
    @Resource
    private UserConverter userConverter;

    @Override
    public SpotCheckAudioModel copySourceToTarget(SpotCheckAudioEntity spotCheckAudioEntity, SpotCheckAudioModel spotCheckAudioModel) {
        spotCheckAudioModel.setSpotCheck(spotCheckConverter.sourceToTarget(spotCheckAudioEntity.getSpotCheck()));
        spotCheckAudioModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(spotCheckAudioEntity.getExtractCheckAudio()));
        spotCheckAudioModel.setUser(userConverter.sourceToTarget(spotCheckAudioEntity.getUser()));
        spotCheckAudioModel.setStatus(spotCheckAudioEntity.getStatus());
        spotCheckAudioModel.setId(spotCheckAudioEntity.getId());
        return spotCheckAudioModel;
    }

    @Override
    public SpotCheckAudioEntity copyTargetToSource(SpotCheckAudioModel spotCheckAudioModel, SpotCheckAudioEntity spotCheckAudioEntity) {
        spotCheckAudioEntity.setSpotCheck(spotCheckConverter.targetToSource(spotCheckAudioModel.getSpotCheck()));
        spotCheckAudioEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(spotCheckAudioModel.getExtractCheckAudio()));
        spotCheckAudioEntity.setUser(userConverter.targetToSource(spotCheckAudioModel.getUser()));
        spotCheckAudioEntity.setStatus(spotCheckAudioModel.getStatus());
        spotCheckAudioEntity.setId(spotCheckAudioModel.getId());
        return spotCheckAudioEntity;
    }
}