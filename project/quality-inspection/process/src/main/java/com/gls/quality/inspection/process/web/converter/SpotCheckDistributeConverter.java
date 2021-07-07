package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckDistributeEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckDistributeModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SpotCheckDistributeConverter implements BaseConverter<SpotCheckDistributeEntity, SpotCheckDistributeModel> {
    @Resource
    private SpotCheckConverter spotCheckConverter;
    @Resource
    private UserConverter userConverter;

    @Override
    public SpotCheckDistributeModel copySourceToTarget(SpotCheckDistributeEntity spotCheckDistributeEntity, SpotCheckDistributeModel spotCheckDistributeModel) {
        spotCheckDistributeModel.setSpotCheck(spotCheckConverter.sourceToTarget(spotCheckDistributeEntity.getSpotCheck()));
        spotCheckDistributeModel.setUser(userConverter.sourceToTarget(spotCheckDistributeEntity.getUser()));
        spotCheckDistributeModel.setDistributeRate(spotCheckDistributeEntity.getDistributeRate());
        spotCheckDistributeModel.setStatus(spotCheckDistributeEntity.getStatus());
        spotCheckDistributeModel.setId(spotCheckDistributeEntity.getId());
        return spotCheckDistributeModel;
    }

    @Override
    public SpotCheckDistributeEntity copyTargetToSource(SpotCheckDistributeModel spotCheckDistributeModel, SpotCheckDistributeEntity spotCheckDistributeEntity) {
        spotCheckDistributeEntity.setSpotCheck(spotCheckConverter.targetToSource(spotCheckDistributeModel.getSpotCheck()));
        spotCheckDistributeEntity.setUser(userConverter.targetToSource(spotCheckDistributeModel.getUser()));
        spotCheckDistributeEntity.setDistributeRate(spotCheckDistributeModel.getDistributeRate());
        spotCheckDistributeEntity.setStatus(spotCheckDistributeModel.getStatus());
        spotCheckDistributeEntity.setId(spotCheckDistributeModel.getId());
        return spotCheckDistributeEntity;
    }
}