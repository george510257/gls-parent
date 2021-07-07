package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SingleTestLogEntity;
import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SingleTestLogConverter implements BaseConverter<SingleTestLogEntity, SingleTestLogModel> {
    @Resource
    private UserConverter userConverter;

    @Override
    public SingleTestLogModel copySourceToTarget(SingleTestLogEntity singleTestLogEntity, SingleTestLogModel singleTestLogModel) {
        singleTestLogModel.setUser(userConverter.sourceToTarget(singleTestLogEntity.getUser()));
        singleTestLogModel.setAsk(singleTestLogEntity.getAsk());
        singleTestLogModel.setAnswer(singleTestLogEntity.getAnswer());
        singleTestLogModel.setRole(singleTestLogEntity.getRole());
        singleTestLogModel.setId(singleTestLogEntity.getId());
        return singleTestLogModel;
    }

    @Override
    public SingleTestLogEntity copyTargetToSource(SingleTestLogModel singleTestLogModel, SingleTestLogEntity singleTestLogEntity) {
        singleTestLogEntity.setUser(userConverter.targetToSource(singleTestLogModel.getUser()));
        singleTestLogEntity.setAsk(singleTestLogModel.getAsk());
        singleTestLogEntity.setAnswer(singleTestLogModel.getAnswer());
        singleTestLogEntity.setRole(singleTestLogModel.getRole());
        singleTestLogEntity.setId(singleTestLogModel.getId());
        return singleTestLogEntity;
    }
}