package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.QingyunEntity;
import com.gls.quality.inspection.process.web.model.QingyunModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class QingyunConverter implements BaseConverter<QingyunEntity, QingyunModel> {
    @Override
    public QingyunModel copySourceToTarget(QingyunEntity entity, QingyunModel model) {
        // todo
        return model;
    }

    @Override
    public QingyunEntity copyTargetToSource(QingyunModel model, QingyunEntity entity) {
        // todo
        return entity;
    }
}
