package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.LoanConverter;
import com.gls.quality.inspection.process.web.entity.LoanEntity;
import com.gls.quality.inspection.process.web.model.LoanModel;
import com.gls.quality.inspection.process.web.model.query.QueryLoanModel;
import com.gls.quality.inspection.process.web.repository.LoanRepository;
import com.gls.quality.inspection.process.web.service.LoanService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class LoanServiceImpl
        extends BaseServiceImpl<LoanRepository, LoanConverter, LoanEntity, LoanModel, QueryLoanModel>
        implements LoanService {
    public LoanServiceImpl(LoanRepository repository, LoanConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<LoanEntity> getSpec(QueryLoanModel queryLoanModel) {
        // todo
        return null;
    }
}
