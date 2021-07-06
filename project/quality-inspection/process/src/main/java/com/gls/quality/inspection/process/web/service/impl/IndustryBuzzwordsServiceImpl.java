package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.IndustryBuzzwordsConverter;
import com.gls.quality.inspection.process.web.entity.IndustryBuzzwordsEntity;
import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.repository.IndustryBuzzwordsRepository;
import com.gls.quality.inspection.process.web.service.IndustryBuzzwordsService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class IndustryBuzzwordsServiceImpl
        extends BaseServiceImpl<IndustryBuzzwordsRepository, IndustryBuzzwordsConverter, IndustryBuzzwordsEntity, IndustryBuzzwordsModel, QueryIndustryBuzzwordsModel>
        implements IndustryBuzzwordsService {
    public IndustryBuzzwordsServiceImpl(IndustryBuzzwordsRepository repository, IndustryBuzzwordsConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<IndustryBuzzwordsEntity> getSpec(QueryIndustryBuzzwordsModel queryIndustryBuzzwordsModel) {
        return null;
    }
}
