package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.CorpusAudioTranforConverter;
import com.gls.quality.inspection.process.web.entity.CorpusAudioTranforEntity;
import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.repository.CorpusAudioTranforRepository;
import com.gls.quality.inspection.process.web.service.CorpusAudioTranforService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class CorpusAudioTranforServiceImpl
        extends BaseServiceImpl<CorpusAudioTranforRepository, CorpusAudioTranforConverter, CorpusAudioTranforEntity, CorpusAudioTranforModel, QueryCorpusAudioTranforModel>
        implements CorpusAudioTranforService {
    public CorpusAudioTranforServiceImpl(CorpusAudioTranforRepository repository, CorpusAudioTranforConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<CorpusAudioTranforEntity> getSpec(QueryCorpusAudioTranforModel queryCorpusAudioTranforModel) {
        return null;
    }
}
