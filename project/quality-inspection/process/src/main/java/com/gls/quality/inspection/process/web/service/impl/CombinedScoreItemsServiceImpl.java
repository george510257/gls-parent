package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CombinedScoreItemsConverter;
import com.gls.quality.inspection.process.web.entity.CombinedScoreItemsEntity;
import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryCombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.repository.CombinedScoreItemsRepository;
import com.gls.quality.inspection.process.web.service.CombinedScoreItemsService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CombinedScoreItemsServiceImpl
        extends BaseServiceImpl<CombinedScoreItemsRepository, CombinedScoreItemsConverter, CombinedScoreItemsEntity, CombinedScoreItemsModel, QueryCombinedScoreItemsModel>
        implements CombinedScoreItemsService {
    public CombinedScoreItemsServiceImpl(CombinedScoreItemsRepository repository, CombinedScoreItemsConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CombinedScoreItemsEntity> getSpec(QueryCombinedScoreItemsModel queryCombinedScoreItemsModel) {
        // todo
        return null;
    }
}
