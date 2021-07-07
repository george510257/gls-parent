package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestTaskEntity;
import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class TotalTestTaskConverter implements BaseConverter<TotalTestTaskEntity, TotalTestTaskModel> {
    @Resource
    private UserConverter userConverter;
    @Resource
    private ModelConverter modelConverter;

    @Override
    public TotalTestTaskModel copySourceToTarget(TotalTestTaskEntity totalTestTaskEntity, TotalTestTaskModel totalTestTaskModel) {
        totalTestTaskModel.setUser(userConverter.sourceToTarget(totalTestTaskEntity.getUser()));
        totalTestTaskModel.setModel(modelConverter.sourceToTarget(totalTestTaskEntity.getModel()));
        totalTestTaskModel.setTestName(totalTestTaskEntity.getTestName());
        totalTestTaskModel.setTestType(totalTestTaskEntity.getTestType());
        totalTestTaskModel.setStatus(totalTestTaskEntity.getStatus());
        totalTestTaskModel.setPassRate(totalTestTaskEntity.getPassRate());
        totalTestTaskModel.setId(totalTestTaskEntity.getId());
        return totalTestTaskModel;
    }

    @Override
    public TotalTestTaskEntity copyTargetToSource(TotalTestTaskModel totalTestTaskModel, TotalTestTaskEntity totalTestTaskEntity) {
        totalTestTaskEntity.setUser(userConverter.targetToSource(totalTestTaskModel.getUser()));
        totalTestTaskEntity.setModel(modelConverter.targetToSource(totalTestTaskModel.getModel()));
        totalTestTaskEntity.setTestName(totalTestTaskModel.getTestName());
        totalTestTaskEntity.setTestType(totalTestTaskModel.getTestType());
        totalTestTaskEntity.setStatus(totalTestTaskModel.getStatus());
        totalTestTaskEntity.setPassRate(totalTestTaskModel.getPassRate());
        totalTestTaskEntity.setId(totalTestTaskModel.getId());
        return totalTestTaskEntity;
    }
}