package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CheckConverter;
import com.gls.quality.inspection.process.web.entity.CheckEntity;
import com.gls.quality.inspection.process.web.model.CheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryCheckModel;
import com.gls.quality.inspection.process.web.repository.CheckRepository;
import com.gls.quality.inspection.process.web.service.CheckService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CheckServiceImpl
        extends BaseServiceImpl<CheckRepository, CheckConverter, CheckEntity, CheckModel, QueryCheckModel>
        implements CheckService {
    public CheckServiceImpl(CheckRepository repository, CheckConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CheckEntity> getSpec(QueryCheckModel queryCheckModel) {
        return null;
    }
}
