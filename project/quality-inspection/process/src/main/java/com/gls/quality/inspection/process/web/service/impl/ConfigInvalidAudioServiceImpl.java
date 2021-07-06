package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ConfigInvalidAudioConverter;
import com.gls.quality.inspection.process.web.entity.ConfigInvalidAudioEntity;
import com.gls.quality.inspection.process.web.model.ConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.repository.ConfigInvalidAudioRepository;
import com.gls.quality.inspection.process.web.service.ConfigInvalidAudioService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ConfigInvalidAudioServiceImpl
        extends BaseServiceImpl<ConfigInvalidAudioRepository, ConfigInvalidAudioConverter, ConfigInvalidAudioEntity, ConfigInvalidAudioModel, QueryConfigInvalidAudioModel>
        implements ConfigInvalidAudioService {
    public ConfigInvalidAudioServiceImpl(ConfigInvalidAudioRepository repository, ConfigInvalidAudioConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ConfigInvalidAudioEntity> getSpec(QueryConfigInvalidAudioModel queryConfigInvalidAudioModel) {
        // todo
        return null;
    }
}
