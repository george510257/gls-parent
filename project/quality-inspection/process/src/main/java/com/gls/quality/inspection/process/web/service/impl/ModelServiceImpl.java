package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ModelConverter;
import com.gls.quality.inspection.process.web.entity.ModelEntity;
import com.gls.quality.inspection.process.web.model.ModelModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelModel;
import com.gls.quality.inspection.process.web.repository.ModelRepository;
import com.gls.quality.inspection.process.web.service.ModelService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ModelServiceImpl
        extends BaseServiceImpl<ModelRepository, ModelConverter, ModelEntity, ModelModel, QueryModelModel>
        implements ModelService {
    public ModelServiceImpl(ModelRepository repository, ModelConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ModelEntity> getSpec(QueryModelModel queryModelModel) {
        // todo
        return null;
    }
}
