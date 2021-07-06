package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.QingyunConverter;
import com.gls.quality.inspection.process.web.entity.QingyunEntity;
import com.gls.quality.inspection.process.web.model.QingyunModel;
import com.gls.quality.inspection.process.web.model.query.QueryQingyunModel;
import com.gls.quality.inspection.process.web.repository.QingyunRepository;
import com.gls.quality.inspection.process.web.service.QingyunService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class QingyunServiceImpl
        extends BaseServiceImpl<QingyunRepository, QingyunConverter, QingyunEntity, QingyunModel, QueryQingyunModel>
        implements QingyunService {
    public QingyunServiceImpl(QingyunRepository repository, QingyunConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<QingyunEntity> getSpec(QueryQingyunModel queryQingyunModel) {
        // todo
        return null;
    }
}
