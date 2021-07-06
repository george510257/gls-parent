package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckResultValidator;
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
@RequestMapping("/extractCheckResult")
public class ExtractCheckResultController {
    @Resource
    private ExtractCheckResultService extractCheckResultService;
    @Resource
    private ExtractCheckResultValidator extractCheckResultValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(extractCheckResultValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ExtractCheckResultModel>> query(QueryExtractCheckResultModel queryExtractCheckResultModel, Pageable pageable) {
        return new Result<>(extractCheckResultService.getPage(queryExtractCheckResultModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ExtractCheckResultModel> getInfo(@PathVariable Long id) {
        return new Result<>(extractCheckResultService.getById(id));
    }

    @PostMapping
    public Result<ExtractCheckResultModel> create(@Valid @RequestBody ExtractCheckResultModel extractCheckResultModel) {
        extractCheckResultService.add(extractCheckResultModel);
        return new Result<>(extractCheckResultService.getById(extractCheckResultModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ExtractCheckResultModel> update(@Valid @RequestBody ExtractCheckResultModel extractCheckResultModel) {
        extractCheckResultService.update(extractCheckResultModel);
        return new Result<>(extractCheckResultService.getById(extractCheckResultModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        extractCheckResultService.remove(id);
        return Result.SUCCESS;
    }
}
