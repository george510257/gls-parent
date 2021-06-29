package com.gls.starter.data.jpa.base;

import com.gls.framework.api.model.BaseModel;
import com.gls.framework.core.base.BaseConverter;
import com.gls.framework.core.exception.GlsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author george
 */
public abstract class BaseServiceImpl<Entity extends BaseEntity, Model extends BaseModel, QueryModel> implements BaseService<Model, QueryModel> {
    private final BaseEntityRepository<Entity> repository;
    private final BaseConverter<Entity, Model> converter;

    public BaseServiceImpl(BaseEntityRepository<Entity> repository, BaseConverter<Entity, Model> converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public List<Model> getAll() {
        return converter.sourceToTargetList(repository.findAll());
    }

    @Override
    public Model getById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlsException("id不可以为null");
        }
        return converter.sourceToTarget(repository.getOne(id));
    }

    @Override
    public Page<Model> getPage(QueryModel model, Pageable pageable) {
        Page<Entity> entityPage = repository.findAll(getSpec(model), pageable);
        return entityPage.map(converter::sourceToTarget);
    }

    @Override
    public void add(Model model) {
        if (!ObjectUtils.isEmpty(model.getId())) {
            throw new GlsException("id重复");
        }
        Entity entity = converter.targetToSource(model);
        repository.save(entity);
    }

    @Override
    public void update(Model model) {
        Entity entity = repository.getOne(model.getId());
        if (ObjectUtils.isEmpty(entity)) {
            throw new GlsException("id不存在");
        }
        entity = converter.copyTargetToSource(model, entity);
        repository.save(entity);
    }

    @Override
    public void remove(Long id) {
        Entity entity = repository.getOne(id);
        if (ObjectUtils.isEmpty(entity)) {
            throw new GlsException("id不存在");
        }
        repository.delete(entity);
    }

    @Override
    public void removeAll(List<Long> ids) {
        List<Entity> entities = repository.findAllById(ids);
        if (!ObjectUtils.isEmpty(entities)) {
            repository.deleteAll(entities);
        }
    }

    /**
     * getSpec
     *
     * @param model
     * @return
     */
    protected abstract Specification<Entity> getSpec(QueryModel model);
}
