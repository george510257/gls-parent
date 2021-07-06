package com.gls.starter.data.jpa.base;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author george
 */
public class BaseController<Service extends BaseService<Model, QueryModel>, Model extends BaseModel, QueryModel> {
    protected Service service;

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<Model>> query(QueryModel queryModel, Pageable pageable) {
        return new Result<>(service.getPage(queryModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<Model> getInfo(@PathVariable Long id) {
        return new Result<>(service.getById(id));
    }

    @PostMapping
    public Result<Model> create(@Valid @RequestBody Model model) {
        service.add(model);
        return new Result<>(service.getById(model.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<Model> update(@Valid @RequestBody Model model) {
        service.update(model);
        return new Result<>(service.getById(model.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        service.remove(id);
        return Result.SUCCESS;
    }
}
