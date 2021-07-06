package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.SingleTestLogConverter;
import com.gls.quality.inspection.process.web.entity.SingleTestLogEntity;
import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QuerySingleTestLogModel;
import com.gls.quality.inspection.process.web.repository.SingleTestLogRepository;
import com.gls.quality.inspection.process.web.service.SingleTestLogService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class SingleTestLogServiceImpl
        extends BaseServiceImpl<SingleTestLogRepository, SingleTestLogConverter, SingleTestLogEntity, SingleTestLogModel, QuerySingleTestLogModel>
        implements SingleTestLogService {
    public SingleTestLogServiceImpl(SingleTestLogRepository repository, SingleTestLogConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<SingleTestLogEntity> getSpec(QuerySingleTestLogModel querySingleTestLogModel) {
        // todo
        return null;
    }
}
