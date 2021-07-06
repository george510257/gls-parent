package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.BaseCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryBaseCategoryModel;
import com.gls.quality.inspection.process.web.service.BaseCategoryService;
import com.gls.quality.inspection.process.web.validator.BaseCategoryValidator;
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
@RequestMapping("/baseCategory")
public class BaseCategoryController {
    @Resource
    private BaseCategoryService baseCategoryService;
    @Resource
    private BaseCategoryValidator baseCategoryValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(baseCategoryValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<BaseCategoryModel>> query(QueryBaseCategoryModel queryBaseCategoryModel, Pageable pageable) {
        return new Result<>(baseCategoryService.getPage(queryBaseCategoryModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<BaseCategoryModel> getInfo(@PathVariable Long id) {
        return new Result<>(baseCategoryService.getById(id));
    }

    @PostMapping
    public Result<BaseCategoryModel> create(@Valid @RequestBody BaseCategoryModel baseCategoryModel) {
        baseCategoryService.add(baseCategoryModel);
        return new Result<>(baseCategoryService.getById(baseCategoryModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<BaseCategoryModel> update(@Valid @RequestBody BaseCategoryModel baseCategoryModel) {
        baseCategoryService.update(baseCategoryModel);
        return new Result<>(baseCategoryService.getById(baseCategoryModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        baseCategoryService.remove(id);
        return Result.SUCCESS;
    }
}
