package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestLogEntity;
import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class TotalTestLogConverter implements BaseConverter<TotalTestLogEntity, TotalTestLogModel> {
    @Resource
    private TotalTestTaskConverter totalTestTaskConverter;

    @Override
    public TotalTestLogModel copySourceToTarget(TotalTestLogEntity totalTestLogEntity, TotalTestLogModel totalTestLogModel) {
        totalTestLogModel.setTotalTestTask(totalTestTaskConverter.sourceToTarget(totalTestLogEntity.getTotalTestTask()));
        totalTestLogModel.setTestContent(totalTestLogEntity.getTestContent());
        totalTestLogModel.setExpectLabel(totalTestLogEntity.getExpectLabel());
        totalTestLogModel.setExpectRole(totalTestLogEntity.getExpectRole());
        totalTestLogModel.setRecLabel(totalTestLogEntity.getRecLabel());
        totalTestLogModel.setRecRule(totalTestLogEntity.getRecRule());
        totalTestLogModel.setRecRole(totalTestLogEntity.getRecRole());
        totalTestLogModel.setModule(totalTestLogEntity.getModule());
        totalTestLogModel.setScore(totalTestLogEntity.getScore());
        totalTestLogModel.setPass(totalTestLogEntity.getPass());
        totalTestLogModel.setId(totalTestLogEntity.getId());
        return totalTestLogModel;
    }

    @Override
    public TotalTestLogEntity copyTargetToSource(TotalTestLogModel totalTestLogModel, TotalTestLogEntity totalTestLogEntity) {
        totalTestLogEntity.setTotalTestTask(totalTestTaskConverter.targetToSource(totalTestLogModel.getTotalTestTask()));
        totalTestLogEntity.setTestContent(totalTestLogModel.getTestContent());
        totalTestLogEntity.setExpectLabel(totalTestLogModel.getExpectLabel());
        totalTestLogEntity.setExpectRole(totalTestLogModel.getExpectRole());
        totalTestLogEntity.setRecLabel(totalTestLogModel.getRecLabel());
        totalTestLogEntity.setRecRule(totalTestLogModel.getRecRule());
        totalTestLogEntity.setRecRole(totalTestLogModel.getRecRole());
        totalTestLogEntity.setModule(totalTestLogModel.getModule());
        totalTestLogEntity.setScore(totalTestLogModel.getScore());
        totalTestLogEntity.setPass(totalTestLogModel.getPass());
        totalTestLogEntity.setId(totalTestLogModel.getId());
        return totalTestLogEntity;
    }
}