package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CorpusConverter;
import com.gls.quality.inspection.process.web.entity.CorpusEntity;
import com.gls.quality.inspection.process.web.model.CorpusModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusModel;
import com.gls.quality.inspection.process.web.repository.CorpusRepository;
import com.gls.quality.inspection.process.web.service.CorpusService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CorpusServiceImpl
        extends BaseServiceImpl<CorpusRepository, CorpusConverter, CorpusEntity, CorpusModel, QueryCorpusModel>
        implements CorpusService {
    public CorpusServiceImpl(CorpusRepository repository, CorpusConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CorpusEntity> getSpec(QueryCorpusModel queryCorpusModel) {
        return null;
    }
}
