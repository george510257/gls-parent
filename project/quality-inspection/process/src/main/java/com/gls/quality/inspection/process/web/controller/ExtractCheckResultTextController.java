package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultTextService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckResultTextValidator;
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
@RequestMapping("/extractCheckResultText")
public class ExtractCheckResultTextController {
    @Resource
    private ExtractCheckResultTextService extractCheckResultTextService;
    @Resource
    private ExtractCheckResultTextValidator extractCheckResultTextValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(extractCheckResultTextValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ExtractCheckResultTextModel>> query(QueryExtractCheckResultTextModel queryExtractCheckResultTextModel, Pageable pageable) {
        return new Result<>(extractCheckResultTextService.getPage(queryExtractCheckResultTextModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ExtractCheckResultTextModel> getInfo(@PathVariable Long id) {
        return new Result<>(extractCheckResultTextService.getById(id));
    }

    @PostMapping
    public Result<ExtractCheckResultTextModel> create(@Valid @RequestBody ExtractCheckResultTextModel extractCheckResultTextModel) {
        extractCheckResultTextService.add(extractCheckResultTextModel);
        return new Result<>(extractCheckResultTextService.getById(extractCheckResultTextModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ExtractCheckResultTextModel> update(@Valid @RequestBody ExtractCheckResultTextModel extractCheckResultTextModel) {
        extractCheckResultTextService.update(extractCheckResultTextModel);
        return new Result<>(extractCheckResultTextService.getById(extractCheckResultTextModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        extractCheckResultTextService.remove(id);
        return Result.SUCCESS;
    }
}
