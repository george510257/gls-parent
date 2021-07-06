package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ConfigWordFilterConverter;
import com.gls.quality.inspection.process.web.entity.ConfigWordFilterEntity;
import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigWordFilterModel;
import com.gls.quality.inspection.process.web.repository.ConfigWordFilterRepository;
import com.gls.quality.inspection.process.web.service.ConfigWordFilterService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ConfigWordFilterServiceImpl
        extends BaseServiceImpl<ConfigWordFilterRepository, ConfigWordFilterConverter, ConfigWordFilterEntity, ConfigWordFilterModel, QueryConfigWordFilterModel>
        implements ConfigWordFilterService {
    public ConfigWordFilterServiceImpl(ConfigWordFilterRepository repository, ConfigWordFilterConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ConfigWordFilterEntity> getSpec(QueryConfigWordFilterModel queryConfigWordFilterModel) {
        return null;
    }
}
