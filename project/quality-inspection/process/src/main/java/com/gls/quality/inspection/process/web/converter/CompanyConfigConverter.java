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
    public CompanyConfigModel copySourceToTarget(CompanyConfigEntity entity, CompanyConfigModel model) {
        return model;
    }

    @Override
    public CompanyConfigEntity copyTargetToSource(CompanyConfigModel model, CompanyConfigEntity entity) {
        return entity;
    }
}
