package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SpecialLabelConverter;
import com.gls.quality.inspection.process.web.entity.SpecialLabelEntity;
import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpecialLabelModel;
import com.gls.quality.inspection.process.web.repository.SpecialLabelRepository;
import com.gls.quality.inspection.process.web.service.SpecialLabelService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SpecialLabelServiceImpl
        extends BaseServiceImpl<SpecialLabelRepository, SpecialLabelConverter, SpecialLabelEntity, SpecialLabelModel, QuerySpecialLabelModel>
        implements SpecialLabelService {
    public SpecialLabelServiceImpl(SpecialLabelRepository repository, SpecialLabelConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SpecialLabelEntity> getSpec(QuerySpecialLabelModel querySpecialLabelModel) {
        return null;
    }
}
