package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CompanyConfigEntity;
import com.gls.quality.inspection.process.web.model.CompanyConfigModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CompanyConfigConverter implements BaseConverter<CompanyConfigEntity, CompanyConfigModel> {
    @Override
    public CompanyConfigModel copySourceToTarget(CompanyConfigEntity companyConfigEntity, CompanyConfigModel companyConfigModel) {
        companyConfigModel.setImgLogo(companyConfigEntity.getImgLogo());
        companyConfigModel.setImgCover(companyConfigEntity.getImgCover());
        companyConfigModel.setTranslateConcurrent(companyConfigEntity.getTranslateConcurrent());
        companyConfigModel.setModelNum(companyConfigEntity.getModelNum());
        companyConfigModel.setAdminNum(companyConfigEntity.getAdminNum());
        companyConfigModel.setCustomerServiceNum(companyConfigEntity.getCustomerServiceNum());
        companyConfigModel.setInspectorNum(companyConfigEntity.getInspectorNum());
        companyConfigModel.setId(companyConfigEntity.getId());
        return companyConfigModel;
    }

    @Override
    public CompanyConfigEntity copyTargetToSource(CompanyConfigModel companyConfigModel, CompanyConfigEntity companyConfigEntity) {
        companyConfigEntity.setImgLogo(companyConfigModel.getImgLogo());
        companyConfigEntity.setImgCover(companyConfigModel.getImgCover());
        companyConfigEntity.setTranslateConcurrent(companyConfigModel.getTranslateConcurrent());
        companyConfigEntity.setModelNum(companyConfigModel.getModelNum());
        companyConfigEntity.setAdminNum(companyConfigModel.getAdminNum());
        companyConfigEntity.setCustomerServiceNum(companyConfigModel.getCustomerServiceNum());
        companyConfigEntity.setInspectorNum(companyConfigModel.getInspectorNum());
        companyConfigEntity.setId(companyConfigModel.getId());
        return companyConfigEntity;
    }
}