package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultTextModel;
import com.gls.quality.inspection.process.web.service.ModelResultTextService;
import com.gls.quality.inspection.process.web.validator.ModelResultTextValidator;
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
@RequestMapping("/modelResultText")
public class ModelResultTextController {
    @Resource
    private ModelResultTextService modelResultTextService;
    @Resource
    private ModelResultTextValidator modelResultTextValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(modelResultTextValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ModelResultTextModel>> query(QueryModelResultTextModel queryModelResultTextModel, Pageable pageable) {
        return new Result<>(modelResultTextService.getPage(queryModelResultTextModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ModelResultTextModel> getInfo(@PathVariable Long id) {
        return new Result<>(modelResultTextService.getById(id));
    }

    @PostMapping
    public Result<ModelResultTextModel> create(@Valid @RequestBody ModelResultTextModel modelResultTextModel) {
        modelResultTextService.add(modelResultTextModel);
        return new Result<>(modelResultTextService.getById(modelResultTextModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ModelResultTextModel> update(@Valid @RequestBody ModelResultTextModel modelResultTextModel) {
        modelResultTextService.update(modelResultTextModel);
        return new Result<>(modelResultTextService.getById(modelResultTextModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        modelResultTextService.remove(id);
        return Result.SUCCESS;
    }
}
