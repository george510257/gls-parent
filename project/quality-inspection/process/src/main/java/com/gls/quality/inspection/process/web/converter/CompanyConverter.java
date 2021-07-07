package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CompanyEntity;
import com.gls.quality.inspection.process.web.model.CompanyModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CompanyConverter implements BaseConverter<CompanyEntity, CompanyModel> {
    @Override
    public CompanyModel copySourceToTarget(CompanyEntity companyEntity, CompanyModel companyModel) {
        companyModel.setExpireTime(companyEntity.getExpireTime());
        companyModel.setStatus(companyEntity.getStatus());
        companyModel.setNameEn(companyEntity.getNameEn());
        companyModel.setApprover(companyEntity.getApprover());
        companyModel.setApplicant(companyEntity.getApplicant());
        companyModel.setType(companyEntity.getType());
        companyModel.setId(companyEntity.getId());
        return companyModel;
    }

    @Override
    public CompanyEntity copyTargetToSource(CompanyModel companyModel, CompanyEntity companyEntity) {
        companyEntity.setExpireTime(companyModel.getExpireTime());
        companyEntity.setStatus(companyModel.getStatus());
        companyEntity.setNameEn(companyModel.getNameEn());
        companyEntity.setApprover(companyModel.getApprover());
        companyEntity.setApplicant(companyModel.getApplicant());
        companyEntity.setType(companyModel.getType());
        companyEntity.setId(companyModel.getId());
        return companyEntity;
    }
}