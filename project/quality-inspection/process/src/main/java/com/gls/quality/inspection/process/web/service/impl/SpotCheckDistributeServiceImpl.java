package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SpotCheckDistributeConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckDistributeEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.repository.SpotCheckDistributeRepository;
import com.gls.quality.inspection.process.web.service.SpotCheckDistributeService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SpotCheckDistributeServiceImpl
        extends BaseServiceImpl<SpotCheckDistributeRepository, SpotCheckDistributeConverter, SpotCheckDistributeEntity, SpotCheckDistributeModel, QuerySpotCheckDistributeModel>
        implements SpotCheckDistributeService {
    public SpotCheckDistributeServiceImpl(SpotCheckDistributeRepository repository, SpotCheckDistributeConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SpotCheckDistributeEntity> getSpec(QuerySpotCheckDistributeModel querySpotCheckDistributeModel) {
        // todo
        return null;
    }
}
