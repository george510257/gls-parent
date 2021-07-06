package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SpotCheckConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckModel;
import com.gls.quality.inspection.process.web.repository.SpotCheckRepository;
import com.gls.quality.inspection.process.web.service.SpotCheckService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SpotCheckServiceImpl
        extends BaseServiceImpl<SpotCheckRepository, SpotCheckConverter, SpotCheckEntity, SpotCheckModel, QuerySpotCheckModel>
        implements SpotCheckService {
    public SpotCheckServiceImpl(SpotCheckRepository repository, SpotCheckConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SpotCheckEntity> getSpec(QuerySpotCheckModel querySpotCheckModel) {
        return null;
    }
}
