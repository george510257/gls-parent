package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CompanyConverter;
import com.gls.quality.inspection.process.web.entity.CompanyEntity;
import com.gls.quality.inspection.process.web.model.CompanyModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyModel;
import com.gls.quality.inspection.process.web.repository.CompanyRepository;
import com.gls.quality.inspection.process.web.service.CompanyService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CompanyServiceImpl
        extends BaseServiceImpl<CompanyRepository, CompanyConverter, CompanyEntity, CompanyModel, QueryCompanyModel>
        implements CompanyService {
    public CompanyServiceImpl(CompanyRepository repository, CompanyConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CompanyEntity> getSpec(QueryCompanyModel queryCompanyModel) {
        return null;
    }
}
