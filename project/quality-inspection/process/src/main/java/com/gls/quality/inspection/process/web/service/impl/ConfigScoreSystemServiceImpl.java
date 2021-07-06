package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ConfigScoreSystemConverter;
import com.gls.quality.inspection.process.web.entity.ConfigScoreSystemEntity;
import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.repository.ConfigScoreSystemRepository;
import com.gls.quality.inspection.process.web.service.ConfigScoreSystemService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ConfigScoreSystemServiceImpl
        extends BaseServiceImpl<ConfigScoreSystemRepository, ConfigScoreSystemConverter, ConfigScoreSystemEntity, ConfigScoreSystemModel, QueryConfigScoreSystemModel>
        implements ConfigScoreSystemService {
    public ConfigScoreSystemServiceImpl(ConfigScoreSystemRepository repository, ConfigScoreSystemConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ConfigScoreSystemEntity> getSpec(QueryConfigScoreSystemModel queryConfigScoreSystemModel) {
        return null;
    }
}
