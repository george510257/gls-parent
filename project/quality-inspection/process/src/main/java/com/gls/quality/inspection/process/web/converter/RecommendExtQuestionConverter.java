package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.RecommendExtQuestionEntity;
import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class RecommendExtQuestionConverter implements BaseConverter<RecommendExtQuestionEntity, RecommendExtQuestionModel> {
    @Override
    public RecommendExtQuestionModel copySourceToTarget(RecommendExtQuestionEntity recommendExtQuestionEntity, RecommendExtQuestionModel recommendExtQuestionModel) {
        recommendExtQuestionModel.setIndustryCategory(recommendExtQuestionEntity.getIndustryCategory());
        recommendExtQuestionModel.setIndustryCategoryIds(recommendExtQuestionEntity.getIndustryCategoryIds());
        recommendExtQuestionModel.setSemanticLabelId(recommendExtQuestionEntity.getSemanticLabelId());
        recommendExtQuestionModel.setPhrasing(recommendExtQuestionEntity.getPhrasing());
        recommendExtQuestionModel.setWordSet(recommendExtQuestionEntity.getWordSet());
        recommendExtQuestionModel.setId(recommendExtQuestionEntity.getId());
        return recommendExtQuestionModel;
    }

    @Override
    public RecommendExtQuestionEntity copyTargetToSource(RecommendExtQuestionModel recommendExtQuestionModel, RecommendExtQuestionEntity recommendExtQuestionEntity) {
        recommendExtQuestionEntity.setIndustryCategory(recommendExtQuestionModel.getIndustryCategory());
        recommendExtQuestionEntity.setIndustryCategoryIds(recommendExtQuestionModel.getIndustryCategoryIds());
        recommendExtQuestionEntity.setSemanticLabelId(recommendExtQuestionModel.getSemanticLabelId());
        recommendExtQuestionEntity.setPhrasing(recommendExtQuestionModel.getPhrasing());
        recommendExtQuestionEntity.setWordSet(recommendExtQuestionModel.getWordSet());
        recommendExtQuestionEntity.setId(recommendExtQuestionModel.getId());
        return recommendExtQuestionEntity;
    }
}