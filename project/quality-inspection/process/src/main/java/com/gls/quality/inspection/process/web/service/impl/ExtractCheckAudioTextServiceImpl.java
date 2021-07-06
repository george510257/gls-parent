package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ExtractCheckAudioTextConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioTextEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.repository.ExtractCheckAudioTextRepository;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioTextService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ExtractCheckAudioTextServiceImpl
        extends BaseServiceImpl<ExtractCheckAudioTextRepository, ExtractCheckAudioTextConverter, ExtractCheckAudioTextEntity, ExtractCheckAudioTextModel, QueryExtractCheckAudioTextModel>
        implements ExtractCheckAudioTextService {
    public ExtractCheckAudioTextServiceImpl(ExtractCheckAudioTextRepository repository, ExtractCheckAudioTextConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ExtractCheckAudioTextEntity> getSpec(QueryExtractCheckAudioTextModel queryExtractCheckAudioTextModel) {
        // todo
        return null;
    }
}
