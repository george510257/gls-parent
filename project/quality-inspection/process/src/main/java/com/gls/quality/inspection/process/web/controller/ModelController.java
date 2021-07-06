package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ModelModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelModel;
import com.gls.quality.inspection.process.web.service.ModelService;
import com.gls.quality.inspection.process.web.validator.ModelValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/model")
public class ModelController {
    @Resource
    private ModelService modelService;
    @Resource
    private ModelValidator modelValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(modelValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ModelModel>> query(QueryModelModel queryModelModel, Pageable pageable) {
        return new Result<>(modelService.getPage(queryModelModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ModelModel> getInfo(@PathVariable Long id) {
        return new Result<>(modelService.getById(id));
    }

    @PostMapping
    public Result<ModelModel> create(@Valid @RequestBody ModelModel modelModel) {
        modelService.add(modelModel);
        return new Result<>(modelService.getById(modelModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ModelModel> update(@Valid @RequestBody ModelModel modelModel) {
        modelService.update(modelModel);
        return new Result<>(modelService.getById(modelModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        modelService.remove(id);
        return Result.SUCCESS;
    }
}
