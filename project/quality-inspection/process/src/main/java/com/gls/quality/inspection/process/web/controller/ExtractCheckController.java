package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckValidator;
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
@RequestMapping("/extractCheck")
public class ExtractCheckController {
    @Resource
    private ExtractCheckService extractCheckService;
    @Resource
    private ExtractCheckValidator extractCheckValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(extractCheckValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ExtractCheckModel>> query(QueryExtractCheckModel queryExtractCheckModel, Pageable pageable) {
        return new Result<>(extractCheckService.getPage(queryExtractCheckModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ExtractCheckModel> getInfo(@PathVariable Long id) {
        return new Result<>(extractCheckService.getById(id));
    }

    @PostMapping
    public Result<ExtractCheckModel> create(@Valid @RequestBody ExtractCheckModel extractCheckModel) {
        extractCheckService.add(extractCheckModel);
        return new Result<>(extractCheckService.getById(extractCheckModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ExtractCheckModel> update(@Valid @RequestBody ExtractCheckModel extractCheckModel) {
        extractCheckService.update(extractCheckModel);
        return new Result<>(extractCheckService.getById(extractCheckModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        extractCheckService.remove(id);
        return Result.SUCCESS;
    }
}
