package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.LabelConverter;
import com.gls.quality.inspection.process.web.entity.LabelEntity;
import com.gls.quality.inspection.process.web.model.LabelModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelModel;
import com.gls.quality.inspection.process.web.repository.LabelRepository;
import com.gls.quality.inspection.process.web.service.LabelService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class LabelServiceImpl
        extends BaseServiceImpl<LabelRepository, LabelConverter, LabelEntity, LabelModel, QueryLabelModel>
        implements LabelService {
    public LabelServiceImpl(LabelRepository repository, LabelConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<LabelEntity> getSpec(QueryLabelModel queryLabelModel) {
        return null;
    }
}
