package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.BaseCategoryConverter;
import com.gls.quality.inspection.process.web.entity.BaseCategoryEntity;
import com.gls.quality.inspection.process.web.model.BaseCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryBaseCategoryModel;
import com.gls.quality.inspection.process.web.repository.BaseCategoryRepository;
import com.gls.quality.inspection.process.web.service.BaseCategoryService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class BaseCategoryServiceImpl
        extends BaseServiceImpl<BaseCategoryRepository, BaseCategoryConverter, BaseCategoryEntity, BaseCategoryModel, QueryBaseCategoryModel>
        implements BaseCategoryService {
    public BaseCategoryServiceImpl(BaseCategoryRepository repository, BaseCategoryConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<BaseCategoryEntity> getSpec(QueryBaseCategoryModel queryBaseCategoryModel) {
        // todo
        return null;
    }
}
