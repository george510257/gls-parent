package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CorpusEntity;
import com.gls.quality.inspection.process.web.model.CorpusModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class CorpusConverter implements BaseConverter<CorpusEntity, CorpusModel> {
    @Resource
    private ModelConverter modelConverter;

    @Override
    public CorpusModel copySourceToTarget(CorpusEntity corpusEntity, CorpusModel corpusModel) {
        corpusModel.setModel(modelConverter.sourceToTarget(corpusEntity.getModel()));
        corpusModel.setContent(corpusEntity.getContent());
        corpusModel.setAudioUrl(corpusEntity.getAudioUrl());
        corpusModel.setTranslateStatus(corpusEntity.getTranslateStatus());
        corpusModel.setStatus(corpusEntity.getStatus());
        corpusModel.setIsAnnotated(corpusEntity.getIsAnnotated());
        corpusModel.setAudioLen(corpusEntity.getAudioLen());
        corpusModel.setTotalLabelId(corpusEntity.getTotalLabelId());
        corpusModel.setComplexLabelId(corpusEntity.getComplexLabelId());
        corpusModel.setSemanticLabelList(corpusEntity.getSemanticLabelList());
        corpusModel.setId(corpusEntity.getId());
        return corpusModel;
    }

    @Override
    public CorpusEntity copyTargetToSource(CorpusModel corpusModel, CorpusEntity corpusEntity) {
        corpusEntity.setModel(modelConverter.targetToSource(corpusModel.getModel()));
        corpusEntity.setContent(corpusModel.getContent());
        corpusEntity.setAudioUrl(corpusModel.getAudioUrl());
        corpusEntity.setTranslateStatus(corpusModel.getTranslateStatus());
        corpusEntity.setStatus(corpusModel.getStatus());
        corpusEntity.setIsAnnotated(corpusModel.getIsAnnotated());
        corpusEntity.setAudioLen(corpusModel.getAudioLen());
        corpusEntity.setTotalLabelId(corpusModel.getTotalLabelId());
        corpusEntity.setComplexLabelId(corpusModel.getComplexLabelId());
        corpusEntity.setSemanticLabelList(corpusModel.getSemanticLabelList());
        corpusEntity.setId(corpusModel.getId());
        return corpusEntity;
    }
}