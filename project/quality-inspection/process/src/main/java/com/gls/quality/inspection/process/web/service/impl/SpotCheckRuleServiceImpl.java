package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SpotCheckRuleConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckRuleEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckRuleModel;
import com.gls.quality.inspection.process.web.repository.SpotCheckRuleRepository;
import com.gls.quality.inspection.process.web.service.SpotCheckRuleService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SpotCheckRuleServiceImpl
        extends BaseServiceImpl<SpotCheckRuleRepository, SpotCheckRuleConverter, SpotCheckRuleEntity, SpotCheckRuleModel, QuerySpotCheckRuleModel>
        implements SpotCheckRuleService {
    public SpotCheckRuleServiceImpl(SpotCheckRuleRepository repository, SpotCheckRuleConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SpotCheckRuleEntity> getSpec(QuerySpotCheckRuleModel querySpotCheckRuleModel) {
        return null;
    }
}
