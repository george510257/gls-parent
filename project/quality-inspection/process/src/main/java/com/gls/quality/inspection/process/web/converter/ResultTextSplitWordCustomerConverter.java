package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordCustomerEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordCustomerConverter implements BaseConverter<ResultTextSplitWordCustomerEntity, ResultTextSplitWordCustomerModel> {
    @Override
    public ResultTextSplitWordCustomerModel copySourceToTarget(ResultTextSplitWordCustomerEntity entity, ResultTextSplitWordCustomerModel model) {
        return model;
    }

    @Override
    public ResultTextSplitWordCustomerEntity copyTargetToSource(ResultTextSplitWordCustomerModel model, ResultTextSplitWordCustomerEntity entity) {
        return entity;
    }
}
