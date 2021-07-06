package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordConverter implements BaseConverter<ResultTextSplitWordEntity, ResultTextSplitWordModel> {
    @Override
    public ResultTextSplitWordModel copySourceToTarget(ResultTextSplitWordEntity entity, ResultTextSplitWordModel model) {
        return model;
    }

    @Override
    public ResultTextSplitWordEntity copyTargetToSource(ResultTextSplitWordModel model, ResultTextSplitWordEntity entity) {
        return entity;
    }
}
