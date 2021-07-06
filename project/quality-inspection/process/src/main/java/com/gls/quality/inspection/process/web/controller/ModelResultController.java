package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ModelResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultModel;
import com.gls.quality.inspection.process.web.service.ModelResultService;
import com.gls.quality.inspection.process.web.validator.ModelResultValidator;
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
@RequestMapping("/modelResult")
public class ModelResultController {
    @Resource
    private ModelResultService modelResultService;
    @Resource
    private ModelResultValidator modelResultValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(modelResultValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ModelResultModel>> query(QueryModelResultModel queryModelResultModel, Pageable pageable) {
        return new Result<>(modelResultService.getPage(queryModelResultModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ModelResultModel> getInfo(@PathVariable Long id) {
        return new Result<>(modelResultService.getById(id));
    }

    @PostMapping
    public Result<ModelResultModel> create(@Valid @RequestBody ModelResultModel modelResultModel) {
        modelResultService.add(modelResultModel);
        return new Result<>(modelResultService.getById(modelResultModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ModelResultModel> update(@Valid @RequestBody ModelResultModel modelResultModel) {
        modelResultService.update(modelResultModel);
        return new Result<>(modelResultService.getById(modelResultModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        modelResultService.remove(id);
        return Result.SUCCESS;
    }
}
