package com.gls.job.admin.web.service.impl;

import com.gls.framework.core.base.BaseConverter;
import com.gls.job.admin.web.service.JobService;
import com.gls.job.core.exception.JobException;
import com.gls.starter.data.jpa.base.BaseEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author george
 */
public abstract class JobServiceImpl<Entity extends BaseEntity, Model, QueryModel> implements JobService<Model, QueryModel> {
    protected BaseEntityRepository<Entity> repository;
    protected BaseConverter<Entity, Model> converter;

    public JobServiceImpl(BaseEntityRepository<Entity> repository, BaseConverter<Entity, Model> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Page<Model> getPage(QueryModel model, Pageable pageable) {
        Page<Entity> entityPage = repository.findAll(getSpec(model), pageable);
        List<Model> modelList = converter.sourceToTargetList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    @Override
    public void add(Model model) {
        Entity entity = converter.targetToSource(model);
        if (!ObjectUtils.isEmpty(entity.getId())) {
            throw new JobException("id重复");
        }
        repository.save(entity);
    }

    @Override
    public void update(Model model) {
        Entity entity = converter.targetToSource(model);
        Entity entityIn = repository.getOne(entity.getId());
        if (!ObjectUtils.isEmpty(entityIn)) {
            entityIn = converter.copyTargetToSource(model, entity);
            repository.save(entityIn);
        }
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    /**
     * getSpec
     *
     * @param model
     * @return
     */
    protected abstract Specification<Entity> getSpec(QueryModel model);
}
