package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.TotalTestTaskConverter;
import com.gls.quality.inspection.process.web.entity.TotalTestTaskEntity;
import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestTaskModel;
import com.gls.quality.inspection.process.web.repository.TotalTestTaskRepository;
import com.gls.quality.inspection.process.web.service.TotalTestTaskService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class TotalTestTaskServiceImpl
        extends BaseServiceImpl<TotalTestTaskRepository, TotalTestTaskConverter, TotalTestTaskEntity, TotalTestTaskModel, QueryTotalTestTaskModel>
        implements TotalTestTaskService {
    public TotalTestTaskServiceImpl(TotalTestTaskRepository repository, TotalTestTaskConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<TotalTestTaskEntity> getSpec(QueryTotalTestTaskModel queryTotalTestTaskModel) {
        return null;
    }
}
