package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigWordFilterModel;
import com.gls.quality.inspection.process.web.service.ConfigWordFilterService;
import com.gls.quality.inspection.process.web.validator.ConfigWordFilterValidator;
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
@RequestMapping("/configWordFilter")
public class ConfigWordFilterController {
    @Resource
    private ConfigWordFilterService configWordFilterService;
    @Resource
    private ConfigWordFilterValidator configWordFilterValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(configWordFilterValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ConfigWordFilterModel>> query(QueryConfigWordFilterModel queryConfigWordFilterModel, Pageable pageable) {
        return new Result<>(configWordFilterService.getPage(queryConfigWordFilterModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ConfigWordFilterModel> getInfo(@PathVariable Long id) {
        return new Result<>(configWordFilterService.getById(id));
    }

    @PostMapping
    public Result<ConfigWordFilterModel> create(@Valid @RequestBody ConfigWordFilterModel configWordFilterModel) {
        configWordFilterService.add(configWordFilterModel);
        return new Result<>(configWordFilterService.getById(configWordFilterModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ConfigWordFilterModel> update(@Valid @RequestBody ConfigWordFilterModel configWordFilterModel) {
        configWordFilterService.update(configWordFilterModel);
        return new Result<>(configWordFilterService.getById(configWordFilterModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        configWordFilterService.remove(id);
        return Result.SUCCESS;
    }
}
