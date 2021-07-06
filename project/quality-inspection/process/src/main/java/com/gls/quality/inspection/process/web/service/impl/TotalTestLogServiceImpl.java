package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.TotalTestLogConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestLogEntity;
import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestLogModel;
import com.gls.quality.inspection.process.web.repository.TotalTestLogRepository;
import com.gls.quality.inspection.process.web.service.TotalTestLogService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class TotalTestLogServiceImpl
        extends BaseServiceImpl<TotalTestLogRepository, TotalTestLogConverter, TotalTestLogEntity, TotalTestLogModel, QueryTotalTestLogModel>
        implements TotalTestLogService {
    public TotalTestLogServiceImpl(TotalTestLogRepository repository, TotalTestLogConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<TotalTestLogEntity> getSpec(QueryTotalTestLogModel queryTotalTestLogModel) {
        // todo
        return null;
    }
}
