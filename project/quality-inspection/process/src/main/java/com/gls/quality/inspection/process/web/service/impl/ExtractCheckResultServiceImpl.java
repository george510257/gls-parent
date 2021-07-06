package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ExtractCheckResultConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultModel;
import com.gls.quality.inspection.process.web.repository.ExtractCheckResultRepository;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ExtractCheckResultServiceImpl
        extends BaseServiceImpl<ExtractCheckResultRepository, ExtractCheckResultConverter, ExtractCheckResultEntity, ExtractCheckResultModel, QueryExtractCheckResultModel>
        implements ExtractCheckResultService {
    public ExtractCheckResultServiceImpl(ExtractCheckResultRepository repository, ExtractCheckResultConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ExtractCheckResultEntity> getSpec(QueryExtractCheckResultModel queryExtractCheckResultModel) {
        return null;
    }
}
