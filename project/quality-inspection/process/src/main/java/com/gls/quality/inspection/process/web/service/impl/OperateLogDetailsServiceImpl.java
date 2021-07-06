package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.OperateLogDetailsConverter;
import com.gls.quality.inspection.process.web.entity.OperateLogDetailsEntity;
import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import com.gls.quality.inspection.process.web.model.query.QueryOperateLogDetailsModel;
import com.gls.quality.inspection.process.web.repository.OperateLogDetailsRepository;
import com.gls.quality.inspection.process.web.service.OperateLogDetailsService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class OperateLogDetailsServiceImpl
        extends BaseServiceImpl<OperateLogDetailsRepository, OperateLogDetailsConverter, OperateLogDetailsEntity, OperateLogDetailsModel, QueryOperateLogDetailsModel>
        implements OperateLogDetailsService {
    public OperateLogDetailsServiceImpl(OperateLogDetailsRepository repository, OperateLogDetailsConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<OperateLogDetailsEntity> getSpec(QueryOperateLogDetailsModel queryOperateLogDetailsModel) {
        // todo
        return null;
    }
}
