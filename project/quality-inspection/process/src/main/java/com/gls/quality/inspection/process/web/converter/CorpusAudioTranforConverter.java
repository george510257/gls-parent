package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.CorpusAudioTranforEntity;
import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CorpusAudioTranforConverter implements BaseConverter<CorpusAudioTranforEntity, CorpusAudioTranforModel> {
    @Override
    public CorpusAudioTranforModel copySourceToTarget(CorpusAudioTranforEntity entity, CorpusAudioTranforModel model) {
        // todo
        return model;
    }

    @Override
    public CorpusAudioTranforEntity copyTargetToSource(CorpusAudioTranforModel model, CorpusAudioTranforEntity entity) {
        // todo
        return entity;
    }
}
