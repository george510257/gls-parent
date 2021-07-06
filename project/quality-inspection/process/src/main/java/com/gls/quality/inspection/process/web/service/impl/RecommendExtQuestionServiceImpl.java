package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.RecommendExtQuestionConverter;
import com.gls.quality.inspection.process.web.entity.RecommendExtQuestionEntity;
import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.model.query.QueryRecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.repository.RecommendExtQuestionRepository;
import com.gls.quality.inspection.process.web.service.RecommendExtQuestionService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class RecommendExtQuestionServiceImpl
        extends BaseServiceImpl<RecommendExtQuestionRepository, RecommendExtQuestionConverter, RecommendExtQuestionEntity, RecommendExtQuestionModel, QueryRecommendExtQuestionModel>
        implements RecommendExtQuestionService {
    public RecommendExtQuestionServiceImpl(RecommendExtQuestionRepository repository, RecommendExtQuestionConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<RecommendExtQuestionEntity> getSpec(QueryRecommendExtQuestionModel queryRecommendExtQuestionModel) {
        // todo
        return null;
    }
}
