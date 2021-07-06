package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ScoreTemplateConverter;
import com.gls.quality.inspection.process.web.entity.ScoreTemplateEntity;
import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreTemplateModel;
import com.gls.quality.inspection.process.web.repository.ScoreTemplateRepository;
import com.gls.quality.inspection.process.web.service.ScoreTemplateService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ScoreTemplateServiceImpl
        extends BaseServiceImpl<ScoreTemplateRepository, ScoreTemplateConverter, ScoreTemplateEntity, ScoreTemplateModel, QueryScoreTemplateModel>
        implements ScoreTemplateService {
    public ScoreTemplateServiceImpl(ScoreTemplateRepository repository, ScoreTemplateConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ScoreTemplateEntity> getSpec(QueryScoreTemplateModel queryScoreTemplateModel) {
        return null;
    }
}
