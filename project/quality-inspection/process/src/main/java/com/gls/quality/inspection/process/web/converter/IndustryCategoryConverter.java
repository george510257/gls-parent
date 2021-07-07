package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IndustryCategoryEntity;
import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class IndustryCategoryConverter implements BaseConverter<IndustryCategoryEntity, IndustryCategoryModel> {
    @Override
    public IndustryCategoryModel copySourceToTarget(IndustryCategoryEntity industryCategoryEntity, IndustryCategoryModel industryCategoryModel) {
        industryCategoryModel.setLevel(industryCategoryEntity.getLevel());
        industryCategoryModel.setParentId(industryCategoryEntity.getParentId());
        industryCategoryModel.setDisplay(industryCategoryEntity.getDisplay());
        industryCategoryModel.setIsUsed(industryCategoryEntity.getIsUsed());
        industryCategoryModel.setId(industryCategoryEntity.getId());
        return industryCategoryModel;
    }

    @Override
    public IndustryCategoryEntity copyTargetToSource(IndustryCategoryModel industryCategoryModel, IndustryCategoryEntity industryCategoryEntity) {
        industryCategoryEntity.setLevel(industryCategoryModel.getLevel());
        industryCategoryEntity.setParentId(industryCategoryModel.getParentId());
        industryCategoryEntity.setDisplay(industryCategoryModel.getDisplay());
        industryCategoryEntity.setIsUsed(industryCategoryModel.getIsUsed());
        industryCategoryEntity.setId(industryCategoryModel.getId());
        return industryCategoryEntity;
    }
}