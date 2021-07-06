package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.IntentionAnalysisConverter;
import com.gls.quality.inspection.process.web.entity.IntentionAnalysisEntity;
import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import com.gls.quality.inspection.process.web.model.query.QueryIntentionAnalysisModel;
import com.gls.quality.inspection.process.web.repository.IntentionAnalysisRepository;
import com.gls.quality.inspection.process.web.service.IntentionAnalysisService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class IntentionAnalysisServiceImpl
        extends BaseServiceImpl<IntentionAnalysisRepository, IntentionAnalysisConverter, IntentionAnalysisEntity, IntentionAnalysisModel, QueryIntentionAnalysisModel>
        implements IntentionAnalysisService {
    public IntentionAnalysisServiceImpl(IntentionAnalysisRepository repository, IntentionAnalysisConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<IntentionAnalysisEntity> getSpec(QueryIntentionAnalysisModel queryIntentionAnalysisModel) {
        // todo
        return null;
    }
}
