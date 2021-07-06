package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.ExtractCheckAudioConverter;
import com.gls.quality.inspection.process.web.entity.ExtractCheckAudioEntity;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.repository.ExtractCheckAudioRepository;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class ExtractCheckAudioServiceImpl
        extends BaseServiceImpl<ExtractCheckAudioRepository, ExtractCheckAudioConverter, ExtractCheckAudioEntity, ExtractCheckAudioModel, QueryExtractCheckAudioModel>
        implements ExtractCheckAudioService {
    public ExtractCheckAudioServiceImpl(ExtractCheckAudioRepository repository, ExtractCheckAudioConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ExtractCheckAudioEntity> getSpec(QueryExtractCheckAudioModel queryExtractCheckAudioModel) {
        // todo
        return null;
    }
}
