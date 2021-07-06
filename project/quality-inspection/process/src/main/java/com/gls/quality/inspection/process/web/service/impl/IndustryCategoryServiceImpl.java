package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.IndustryCategoryConverter;
import com.gls.quality.inspection.process.web.entity.IndustryCategoryEntity;
import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryCategoryModel;
import com.gls.quality.inspection.process.web.repository.IndustryCategoryRepository;
import com.gls.quality.inspection.process.web.service.IndustryCategoryService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class IndustryCategoryServiceImpl
        extends BaseServiceImpl<IndustryCategoryRepository, IndustryCategoryConverter, IndustryCategoryEntity, IndustryCategoryModel, QueryIndustryCategoryModel>
        implements IndustryCategoryService {
    public IndustryCategoryServiceImpl(IndustryCategoryRepository repository, IndustryCategoryConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<IndustryCategoryEntity> getSpec(QueryIndustryCategoryModel queryIndustryCategoryModel) {
        return null;
    }
}
