package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ConfigChannelConverter;
import com.gls.quality.inspection.process.web.entity.ConfigChannelEntity;
import com.gls.quality.inspection.process.web.model.ConfigChannelModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigChannelModel;
import com.gls.quality.inspection.process.web.repository.ConfigChannelRepository;
import com.gls.quality.inspection.process.web.service.ConfigChannelService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ConfigChannelServiceImpl
        extends BaseServiceImpl<ConfigChannelRepository, ConfigChannelConverter, ConfigChannelEntity, ConfigChannelModel, QueryConfigChannelModel>
        implements ConfigChannelService {
    public ConfigChannelServiceImpl(ConfigChannelRepository repository, ConfigChannelConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ConfigChannelEntity> getSpec(QueryConfigChannelModel queryConfigChannelModel) {
        return null;
    }
}
