package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.DictionaryConverter;
import com.gls.quality.inspection.process.web.entity.DictionaryEntity;
import com.gls.quality.inspection.process.web.model.DictionaryModel;
import com.gls.quality.inspection.process.web.model.query.QueryDictionaryModel;
import com.gls.quality.inspection.process.web.repository.DictionaryRepository;
import com.gls.quality.inspection.process.web.service.DictionaryService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class DictionaryServiceImpl
        extends BaseServiceImpl<DictionaryRepository, DictionaryConverter, DictionaryEntity, DictionaryModel, QueryDictionaryModel>
        implements DictionaryService {
    public DictionaryServiceImpl(DictionaryRepository repository, DictionaryConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<DictionaryEntity> getSpec(QueryDictionaryModel queryDictionaryModel) {
        return null;
    }
}
