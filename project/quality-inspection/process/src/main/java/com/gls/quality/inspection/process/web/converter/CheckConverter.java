package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CheckEntity;
import com.gls.quality.inspection.process.web.model.CheckModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CheckConverter implements BaseConverter<CheckEntity, CheckModel> {
    @Override
    public CheckModel copySourceToTarget(CheckEntity checkEntity, CheckModel checkModel) {
        checkModel.setApplyCode(checkEntity.getApplyCode());
        checkModel.setNode(checkEntity.getNode());
        checkModel.setCustomerName(checkEntity.getCustomerName());
        checkModel.setIdCard(checkEntity.getIdCard());
        checkModel.setProductName(checkEntity.getProductName());
        checkModel.setCompanyName(checkEntity.getCompanyName());
        checkModel.setCheckType(checkEntity.getCheckType());
        checkModel.setStatus(checkEntity.getStatus());
        checkModel.setId(checkEntity.getId());
        return checkModel;
    }

    @Override
    public CheckEntity copyTargetToSource(CheckModel checkModel, CheckEntity checkEntity) {
        checkEntity.setApplyCode(checkModel.getApplyCode());
        checkEntity.setNode(checkModel.getNode());
        checkEntity.setCustomerName(checkModel.getCustomerName());
        checkEntity.setIdCard(checkModel.getIdCard());
        checkEntity.setProductName(checkModel.getProductName());
        checkEntity.setCompanyName(checkModel.getCompanyName());
        checkEntity.setCheckType(checkModel.getCheckType());
        checkEntity.setStatus(checkModel.getStatus());
        checkEntity.setId(checkModel.getId());
        return checkEntity;
    }
}