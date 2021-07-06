package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ResultTextSplitWordConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.repository.ResultTextSplitWordRepository;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ResultTextSplitWordServiceImpl
        extends BaseServiceImpl<ResultTextSplitWordRepository, ResultTextSplitWordConverter, ResultTextSplitWordEntity, ResultTextSplitWordModel, QueryResultTextSplitWordModel>
        implements ResultTextSplitWordService {
    public ResultTextSplitWordServiceImpl(ResultTextSplitWordRepository repository, ResultTextSplitWordConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ResultTextSplitWordEntity> getSpec(QueryResultTextSplitWordModel queryResultTextSplitWordModel) {
        // todo
        return null;
    }
}
