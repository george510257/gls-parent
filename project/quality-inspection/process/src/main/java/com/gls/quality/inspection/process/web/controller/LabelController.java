package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.LabelModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelModel;
import com.gls.quality.inspection.process.web.service.LabelService;
import com.gls.quality.inspection.process.web.validator.LabelValidator;
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
@RequestMapping("/label")
public class LabelController {
    @Resource
    private LabelService labelService;
    @Resource
    private LabelValidator labelValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(labelValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<LabelModel>> query(QueryLabelModel queryLabelModel, Pageable pageable) {
        return new Result<>(labelService.getPage(queryLabelModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<LabelModel> getInfo(@PathVariable Long id) {
        return new Result<>(labelService.getById(id));
    }

    @PostMapping
    public Result<LabelModel> create(@Valid @RequestBody LabelModel labelModel) {
        labelService.add(labelModel);
        return new Result<>(labelService.getById(labelModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<LabelModel> update(@Valid @RequestBody LabelModel labelModel) {
        labelService.update(labelModel);
        return new Result<>(labelService.getById(labelModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        labelService.remove(id);
        return Result.SUCCESS;
    }
}
