package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SectionAnnotatedConverter;
import com.gls.quality.inspection.process.web.entity.SectionAnnotatedEntity;
import com.gls.quality.inspection.process.web.model.SectionAnnotatedModel;
import com.gls.quality.inspection.process.web.model.query.QuerySectionAnnotatedModel;
import com.gls.quality.inspection.process.web.repository.SectionAnnotatedRepository;
import com.gls.quality.inspection.process.web.service.SectionAnnotatedService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SectionAnnotatedServiceImpl
        extends BaseServiceImpl<SectionAnnotatedRepository, SectionAnnotatedConverter, SectionAnnotatedEntity, SectionAnnotatedModel, QuerySectionAnnotatedModel>
        implements SectionAnnotatedService {
    public SectionAnnotatedServiceImpl(SectionAnnotatedRepository repository, SectionAnnotatedConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SectionAnnotatedEntity> getSpec(QuerySectionAnnotatedModel querySectionAnnotatedModel) {
        return null;
    }
}
