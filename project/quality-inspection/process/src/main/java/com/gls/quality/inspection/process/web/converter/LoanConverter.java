package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LoanEntity;
import com.gls.quality.inspection.process.web.model.LoanModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class LoanConverter implements BaseConverter<LoanEntity, LoanModel> {
    @Override
    public LoanModel copySourceToTarget(LoanEntity entity, LoanModel model) {
        return model;
    }

    @Override
    public LoanEntity copyTargetToSource(LoanModel model, LoanEntity entity) {
        return entity;
    }
}
