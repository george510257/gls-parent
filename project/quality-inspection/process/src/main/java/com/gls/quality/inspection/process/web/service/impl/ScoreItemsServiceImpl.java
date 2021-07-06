package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ScoreItemsConverter;
import com.gls.quality.inspection.process.web.entity.ScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreItemsModel;
import com.gls.quality.inspection.process.web.repository.ScoreItemsRepository;
import com.gls.quality.inspection.process.web.service.ScoreItemsService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ScoreItemsServiceImpl
        extends BaseServiceImpl<ScoreItemsRepository, ScoreItemsConverter, ScoreItemsEntity, ScoreItemsModel, QueryScoreItemsModel>
        implements ScoreItemsService {
    public ScoreItemsServiceImpl(ScoreItemsRepository repository, ScoreItemsConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ScoreItemsEntity> getSpec(QueryScoreItemsModel queryScoreItemsModel) {
        return null;
    }
}
