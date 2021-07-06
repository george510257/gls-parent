package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ModelResultConverter;
import com.gls.quality.inspection.process.web.entity.ModelResultEntity;
import com.gls.quality.inspection.process.web.model.ModelResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultModel;
import com.gls.quality.inspection.process.web.repository.ModelResultRepository;
import com.gls.quality.inspection.process.web.service.ModelResultService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ModelResultServiceImpl
        extends BaseServiceImpl<ModelResultRepository, ModelResultConverter, ModelResultEntity, ModelResultModel, QueryModelResultModel>
        implements ModelResultService {
    public ModelResultServiceImpl(ModelResultRepository repository, ModelResultConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ModelResultEntity> getSpec(QueryModelResultModel queryModelResultModel) {
        return null;
    }
}
