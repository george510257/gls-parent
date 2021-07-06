package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.LabelTestDataConverter;
import com.gls.quality.inspection.process.web.entity.LabelTestDataEntity;
import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelTestDataModel;
import com.gls.quality.inspection.process.web.repository.LabelTestDataRepository;
import com.gls.quality.inspection.process.web.service.LabelTestDataService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class LabelTestDataServiceImpl
        extends BaseServiceImpl<LabelTestDataRepository, LabelTestDataConverter, LabelTestDataEntity, LabelTestDataModel, QueryLabelTestDataModel>
        implements LabelTestDataService {
    public LabelTestDataServiceImpl(LabelTestDataRepository repository, LabelTestDataConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<LabelTestDataEntity> getSpec(QueryLabelTestDataModel queryLabelTestDataModel) {
        return null;
    }
}
