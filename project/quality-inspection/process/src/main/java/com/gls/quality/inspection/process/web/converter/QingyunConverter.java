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
    public QingyunModel copySourceToTarget(QingyunEntity qingyunEntity, QingyunModel qingyunModel) {
        qingyunModel.setDir(qingyunEntity.getDir());
        qingyunModel.setFileUrl(qingyunEntity.getFileUrl());
        qingyunModel.setCheckNo(qingyunEntity.getCheckNo());
        qingyunModel.setId(qingyunEntity.getId());
        return qingyunModel;
    }

    @Override
    public QingyunEntity copyTargetToSource(QingyunModel qingyunModel, QingyunEntity qingyunEntity) {
        qingyunEntity.setDir(qingyunModel.getDir());
        qingyunEntity.setFileUrl(qingyunModel.getFileUrl());
        qingyunEntity.setCheckNo(qingyunModel.getCheckNo());
        qingyunEntity.setId(qingyunModel.getId());
        return qingyunEntity;
    }
}