package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.BusinessLineConverter;
import com.gls.quality.inspection.process.web.entity.BusinessLineEntity;
import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import com.gls.quality.inspection.process.web.model.query.QueryBusinessLineModel;
import com.gls.quality.inspection.process.web.repository.BusinessLineRepository;
import com.gls.quality.inspection.process.web.service.BusinessLineService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class BusinessLineServiceImpl
        extends BaseServiceImpl<BusinessLineRepository, BusinessLineConverter, BusinessLineEntity, BusinessLineModel, QueryBusinessLineModel>
        implements BusinessLineService {
    public BusinessLineServiceImpl(BusinessLineRepository repository, BusinessLineConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<BusinessLineEntity> getSpec(QueryBusinessLineModel queryBusinessLineModel) {
        // todo
        return null;
    }
}
