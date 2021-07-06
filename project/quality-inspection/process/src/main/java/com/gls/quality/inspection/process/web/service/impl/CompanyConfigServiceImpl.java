package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CompanyConfigConverter;
import com.gls.quality.inspection.process.web.entity.CompanyConfigEntity;
import com.gls.quality.inspection.process.web.model.CompanyConfigModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyConfigModel;
import com.gls.quality.inspection.process.web.repository.CompanyConfigRepository;
import com.gls.quality.inspection.process.web.service.CompanyConfigService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CompanyConfigServiceImpl
        extends BaseServiceImpl<CompanyConfigRepository, CompanyConfigConverter, CompanyConfigEntity, CompanyConfigModel, QueryCompanyConfigModel>
        implements CompanyConfigService {
    public CompanyConfigServiceImpl(CompanyConfigRepository repository, CompanyConfigConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CompanyConfigEntity> getSpec(QueryCompanyConfigModel queryCompanyConfigModel) {
        return null;
    }
}
