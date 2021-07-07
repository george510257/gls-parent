package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SectionAnnotatedEntity;
import com.gls.quality.inspection.process.web.model.SectionAnnotatedModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SectionAnnotatedConverter implements BaseConverter<SectionAnnotatedEntity, SectionAnnotatedModel> {
    @Resource
    private CorpusConverter corpusConverter;

    @Override
    public SectionAnnotatedModel copySourceToTarget(SectionAnnotatedEntity sectionAnnotatedEntity, SectionAnnotatedModel sectionAnnotatedModel) {
        sectionAnnotatedModel.setCorpus(corpusConverter.sourceToTarget(sectionAnnotatedEntity.getCorpus()));
        sectionAnnotatedModel.setSelection(sectionAnnotatedEntity.getSelection());
        sectionAnnotatedModel.setTotalLabel(sectionAnnotatedEntity.getTotalLabel());
        sectionAnnotatedModel.setSingleLabel(sectionAnnotatedEntity.getSingleLabel());
        sectionAnnotatedModel.setComplexLabel(sectionAnnotatedEntity.getComplexLabel());
        sectionAnnotatedModel.setExtqRule(sectionAnnotatedEntity.getExtqRule());
        sectionAnnotatedModel.setId(sectionAnnotatedEntity.getId());
        return sectionAnnotatedModel;
    }

    @Override
    public SectionAnnotatedEntity copyTargetToSource(SectionAnnotatedModel sectionAnnotatedModel, SectionAnnotatedEntity sectionAnnotatedEntity) {
        sectionAnnotatedEntity.setCorpus(corpusConverter.targetToSource(sectionAnnotatedModel.getCorpus()));
        sectionAnnotatedEntity.setSelection(sectionAnnotatedModel.getSelection());
        sectionAnnotatedEntity.setTotalLabel(sectionAnnotatedModel.getTotalLabel());
        sectionAnnotatedEntity.setSingleLabel(sectionAnnotatedModel.getSingleLabel());
        sectionAnnotatedEntity.setComplexLabel(sectionAnnotatedModel.getComplexLabel());
        sectionAnnotatedEntity.setExtqRule(sectionAnnotatedModel.getExtqRule());
        sectionAnnotatedEntity.setId(sectionAnnotatedModel.getId());
        return sectionAnnotatedEntity;
    }
}