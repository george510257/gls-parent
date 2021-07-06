package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ModelResultTextConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultTextEntity;
import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultTextModel;
import com.gls.quality.inspection.process.web.repository.ModelResultTextRepository;
import com.gls.quality.inspection.process.web.service.ModelResultTextService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ModelResultTextServiceImpl
        extends BaseServiceImpl<ModelResultTextRepository, ModelResultTextConverter, ModelResultTextEntity, ModelResultTextModel, QueryModelResultTextModel>
        implements ModelResultTextService {
    public ModelResultTextServiceImpl(ModelResultTextRepository repository, ModelResultTextConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ModelResultTextEntity> getSpec(QueryModelResultTextModel queryModelResultTextModel) {
        return null;
    }
}
