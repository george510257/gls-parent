package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ResultTextSplitWordCustomerConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordCustomerEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.repository.ResultTextSplitWordCustomerRepository;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordCustomerService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ResultTextSplitWordCustomerServiceImpl
        extends BaseServiceImpl<ResultTextSplitWordCustomerRepository, ResultTextSplitWordCustomerConverter, ResultTextSplitWordCustomerEntity, ResultTextSplitWordCustomerModel, QueryResultTextSplitWordCustomerModel>
        implements ResultTextSplitWordCustomerService {
    public ResultTextSplitWordCustomerServiceImpl(ResultTextSplitWordCustomerRepository repository, ResultTextSplitWordCustomerConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ResultTextSplitWordCustomerEntity> getSpec(QueryResultTextSplitWordCustomerModel queryResultTextSplitWordCustomerModel) {
        return null;
    }
}
