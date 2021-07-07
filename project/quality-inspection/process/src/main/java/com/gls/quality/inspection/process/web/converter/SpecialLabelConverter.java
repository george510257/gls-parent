package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpecialLabelEntity;
import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SpecialLabelConverter implements BaseConverter<SpecialLabelEntity, SpecialLabelModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public SpecialLabelModel copySourceToTarget(SpecialLabelEntity specialLabelEntity, SpecialLabelModel specialLabelModel) {
        specialLabelModel.setModel(modelConverter.sourceToTarget(specialLabelEntity.getModel()));
        specialLabelModel.setType(specialLabelEntity.getType());
        specialLabelModel.setCallIds(specialLabelEntity.getCallIds());
        specialLabelModel.setStatus(specialLabelEntity.getStatus());
        specialLabelModel.setChildLabel(specialLabelEntity.getChildLabel());
        specialLabelModel.setDigest(specialLabelEntity.getDigest());
        specialLabelModel.setId(specialLabelEntity.getId());
        return specialLabelModel;
    }

    @Override
    public SpecialLabelEntity copyTargetToSource(SpecialLabelModel specialLabelModel, SpecialLabelEntity specialLabelEntity) {
        specialLabelEntity.setModel(modelConverter.targetToSource(specialLabelModel.getModel()));
        specialLabelEntity.setType(specialLabelModel.getType());
        specialLabelEntity.setCallIds(specialLabelModel.getCallIds());
        specialLabelEntity.setStatus(specialLabelModel.getStatus());
        specialLabelEntity.setChildLabel(specialLabelModel.getChildLabel());
        specialLabelEntity.setDigest(specialLabelModel.getDigest());
        specialLabelEntity.setId(specialLabelModel.getId());
        return specialLabelEntity;
    }
}