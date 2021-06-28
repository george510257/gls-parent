package com.gls.starter.data.jpa.base;

import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.exception.GlsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * @author george
 */
public abstract class BaseServiceImpl<Entity extends BaseEntity, Model, QueryModel> implements BaseService<Model, QueryModel> {
    private final BaseEntityRepository<Entity> repository;
    private final BaseConverter<Entity, Model> converter;

    public BaseServiceImpl(BaseEntityRepository<Entity> repository, BaseConverter<Entity, Model> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Page<Model> getPage(QueryModel model, Pageable pageable) {
        Page<Entity> entityPage = repository.findAll(getSpec(model), pageable);
        return entityPage.map(converter::sourceToTarget);
    }

    @Override
    public void add(Model model) {
        Entity entity = converter.targetToSource(model);
        if (!ObjectUtils.isEmpty(entity.getId())) {
            throw new GlsException("id重复");
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
