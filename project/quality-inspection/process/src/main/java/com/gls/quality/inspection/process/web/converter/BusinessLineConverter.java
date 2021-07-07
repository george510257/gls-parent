package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.BusinessLineEntity;
import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class BusinessLineConverter implements BaseConverter<BusinessLineEntity, BusinessLineModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public BusinessLineModel copySourceToTarget(BusinessLineEntity businessLineEntity, BusinessLineModel businessLineModel) {
        businessLineModel.setBelType(businessLineEntity.getBelType());
        businessLineModel.setCheckType(businessLineEntity.getCheckType());
        businessLineModel.setPurpose(businessLineEntity.getPurpose());
        businessLineModel.setPayType(businessLineEntity.getPayType());
        businessLineModel.setModel(modelConverter.sourceToTarget(businessLineEntity.getModel()));
        businessLineModel.setCount(businessLineEntity.getCount());
        businessLineModel.setRemark(businessLineEntity.getRemark());
        businessLineModel.setId(businessLineEntity.getId());
        return businessLineModel;
    }

    @Override
    public BusinessLineEntity copyTargetToSource(BusinessLineModel businessLineModel, BusinessLineEntity businessLineEntity) {
        businessLineEntity.setBelType(businessLineModel.getBelType());
        businessLineEntity.setCheckType(businessLineModel.getCheckType());
        businessLineEntity.setPurpose(businessLineModel.getPurpose());
        businessLineEntity.setPayType(businessLineModel.getPayType());
        businessLineEntity.setModel(modelConverter.targetToSource(businessLineModel.getModel()));
        businessLineEntity.setCount(businessLineModel.getCount());
        businessLineEntity.setRemark(businessLineModel.getRemark());
        businessLineEntity.setId(businessLineModel.getId());
        return businessLineEntity;
    }
}