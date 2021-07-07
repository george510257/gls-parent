package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.OperateLogDetailsEntity;
import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class OperateLogDetailsConverter implements BaseConverter<OperateLogDetailsEntity, OperateLogDetailsModel> {
    @Override
    public OperateLogDetailsModel copySourceToTarget(OperateLogDetailsEntity operateLogDetailsEntity, OperateLogDetailsModel operateLogDetailsModel) {
        operateLogDetailsModel.setModule(operateLogDetailsEntity.getModule());
        operateLogDetailsModel.setOperateName(operateLogDetailsEntity.getOperateName());
        operateLogDetailsModel.setLogDetails(operateLogDetailsEntity.getLogDetails());
        operateLogDetailsModel.setCreateBy(operateLogDetailsEntity.getCreateBy());
        operateLogDetailsModel.setUsername(operateLogDetailsEntity.getUsername());
        operateLogDetailsModel.setRealname(operateLogDetailsEntity.getRealname());
        operateLogDetailsModel.setId(operateLogDetailsEntity.getId());
        return operateLogDetailsModel;
    }

    @Override
    public OperateLogDetailsEntity copyTargetToSource(OperateLogDetailsModel operateLogDetailsModel, OperateLogDetailsEntity operateLogDetailsEntity) {
        operateLogDetailsEntity.setModule(operateLogDetailsModel.getModule());
        operateLogDetailsEntity.setOperateName(operateLogDetailsModel.getOperateName());
        operateLogDetailsEntity.setLogDetails(operateLogDetailsModel.getLogDetails());
        operateLogDetailsEntity.setCreateBy(operateLogDetailsModel.getCreateBy());
        operateLogDetailsEntity.setUsername(operateLogDetailsModel.getUsername());
        operateLogDetailsEntity.setRealname(operateLogDetailsModel.getRealname());
        operateLogDetailsEntity.setId(operateLogDetailsModel.getId());
        return operateLogDetailsEntity;
    }
}