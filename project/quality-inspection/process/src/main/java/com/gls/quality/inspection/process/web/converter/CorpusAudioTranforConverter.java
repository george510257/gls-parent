package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CorpusAudioTranforEntity;
import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class CorpusAudioTranforConverter implements BaseConverter<CorpusAudioTranforEntity, CorpusAudioTranforModel> {
    @Resource
    private CorpusConverter corpusConverter;

    @Override
    public CorpusAudioTranforModel copySourceToTarget(CorpusAudioTranforEntity corpusAudioTranforEntity, CorpusAudioTranforModel corpusAudioTranforModel) {
        corpusAudioTranforModel.setCorpus(corpusConverter.sourceToTarget(corpusAudioTranforEntity.getCorpus()));
        corpusAudioTranforModel.setLid(corpusAudioTranforEntity.getLid());
        corpusAudioTranforModel.setBegin(corpusAudioTranforEntity.getBegin());
        corpusAudioTranforModel.setEnd(corpusAudioTranforEntity.getEnd());
        corpusAudioTranforModel.setOnebest(corpusAudioTranforEntity.getOnebest());
        corpusAudioTranforModel.setSc(corpusAudioTranforEntity.getSc());
        corpusAudioTranforModel.setSpk(corpusAudioTranforEntity.getSpk());
        corpusAudioTranforModel.setId(corpusAudioTranforEntity.getId());
        return corpusAudioTranforModel;
    }

    @Override
    public CorpusAudioTranforEntity copyTargetToSource(CorpusAudioTranforModel corpusAudioTranforModel, CorpusAudioTranforEntity corpusAudioTranforEntity) {
        corpusAudioTranforEntity.setCorpus(corpusConverter.targetToSource(corpusAudioTranforModel.getCorpus()));
        corpusAudioTranforEntity.setLid(corpusAudioTranforModel.getLid());
        corpusAudioTranforEntity.setBegin(corpusAudioTranforModel.getBegin());
        corpusAudioTranforEntity.setEnd(corpusAudioTranforModel.getEnd());
        corpusAudioTranforEntity.setOnebest(corpusAudioTranforModel.getOnebest());
        corpusAudioTranforEntity.setSc(corpusAudioTranforModel.getSc());
        corpusAudioTranforEntity.setSpk(corpusAudioTranforModel.getSpk());
        corpusAudioTranforEntity.setId(corpusAudioTranforModel.getId());
        return corpusAudioTranforEntity;
    }
}