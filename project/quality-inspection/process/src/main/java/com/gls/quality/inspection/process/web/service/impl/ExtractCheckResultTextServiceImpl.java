package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ExtractCheckResultTextConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckResultTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.repository.ExtractCheckResultTextRepository;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultTextService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ExtractCheckResultTextServiceImpl
        extends BaseServiceImpl<ExtractCheckResultTextRepository, ExtractCheckResultTextConverter, ExtractCheckResultTextEntity, ExtractCheckResultTextModel, QueryExtractCheckResultTextModel>
        implements ExtractCheckResultTextService {
    public ExtractCheckResultTextServiceImpl(ExtractCheckResultTextRepository repository, ExtractCheckResultTextConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ExtractCheckResultTextEntity> getSpec(QueryExtractCheckResultTextModel queryExtractCheckResultTextModel) {
        // todo
        return null;
    }
}
