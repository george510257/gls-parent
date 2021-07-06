package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ExtractCheckConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckModel;
import com.gls.quality.inspection.process.web.repository.ExtractCheckRepository;
import com.gls.quality.inspection.process.web.service.ExtractCheckService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ExtractCheckServiceImpl
        extends BaseServiceImpl<ExtractCheckRepository, ExtractCheckConverter, ExtractCheckEntity, ExtractCheckModel, QueryExtractCheckModel>
        implements ExtractCheckService {
    public ExtractCheckServiceImpl(ExtractCheckRepository repository, ExtractCheckConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ExtractCheckEntity> getSpec(QueryExtractCheckModel queryExtractCheckModel) {
        return null;
    }
}
