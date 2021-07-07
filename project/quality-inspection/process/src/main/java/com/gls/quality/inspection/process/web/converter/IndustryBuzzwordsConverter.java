package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.IndustryBuzzwordsEntity;
import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class IndustryBuzzwordsConverter implements BaseConverter<IndustryBuzzwordsEntity, IndustryBuzzwordsModel> {
    @Resource
    private IndustryCategoryConverter industryCategoryConverter;

    @Override
    public IndustryBuzzwordsModel copySourceToTarget(IndustryBuzzwordsEntity industryBuzzwordsEntity, IndustryBuzzwordsModel industryBuzzwordsModel) {
        industryBuzzwordsModel.setIndustryCategory(industryCategoryConverter.sourceToTarget(industryBuzzwordsEntity.getIndustryCategory()));
        industryBuzzwordsModel.setIndustryCategoryIds(industryBuzzwordsEntity.getIndustryCategoryIds());
        industryBuzzwordsModel.setBuzzwordsText(industryBuzzwordsEntity.getBuzzwordsText());
        industryBuzzwordsModel.setBuzzwordsNumber(industryBuzzwordsEntity.getBuzzwordsNumber());
        industryBuzzwordsModel.setId(industryBuzzwordsEntity.getId());
        return industryBuzzwordsModel;
    }

    @Override
    public IndustryBuzzwordsEntity copyTargetToSource(IndustryBuzzwordsModel industryBuzzwordsModel, IndustryBuzzwordsEntity industryBuzzwordsEntity) {
        industryBuzzwordsEntity.setIndustryCategory(industryCategoryConverter.targetToSource(industryBuzzwordsModel.getIndustryCategory()));
        industryBuzzwordsEntity.setIndustryCategoryIds(industryBuzzwordsModel.getIndustryCategoryIds());
        industryBuzzwordsEntity.setBuzzwordsText(industryBuzzwordsModel.getBuzzwordsText());
        industryBuzzwordsEntity.setBuzzwordsNumber(industryBuzzwordsModel.getBuzzwordsNumber());
        industryBuzzwordsEntity.setId(industryBuzzwordsModel.getId());
        return industryBuzzwordsEntity;
    }
}