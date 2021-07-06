package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SpotCheckAudioConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckAudioModel;
import com.gls.quality.inspection.process.web.repository.SpotCheckAudioRepository;
import com.gls.quality.inspection.process.web.service.SpotCheckAudioService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SpotCheckAudioServiceImpl
        extends BaseServiceImpl<SpotCheckAudioRepository, SpotCheckAudioConverter, SpotCheckAudioEntity, SpotCheckAudioModel, QuerySpotCheckAudioModel>
        implements SpotCheckAudioService {
    public SpotCheckAudioServiceImpl(SpotCheckAudioRepository repository, SpotCheckAudioConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SpotCheckAudioEntity> getSpec(QuerySpotCheckAudioModel querySpotCheckAudioModel) {
        // todo
        return null;
    }
}
