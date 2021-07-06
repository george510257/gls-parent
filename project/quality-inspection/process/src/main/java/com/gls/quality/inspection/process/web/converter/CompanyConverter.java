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
    public CompanyModel copySourceToTarget(CompanyEntity entity, CompanyModel model) {
        // todo
        return model;
    }

    @Override
    public CompanyEntity copyTargetToSource(CompanyModel model, CompanyEntity entity) {
        // todo
        return entity;
    }
}
