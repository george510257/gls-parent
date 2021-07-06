package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpecialLabelModel;
import com.gls.quality.inspection.process.web.service.SpecialLabelService;
import com.gls.quality.inspection.process.web.validator.SpecialLabelValidator;
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
@RequestMapping("/specialLabel")
public class SpecialLabelController {
    @Resource
    private SpecialLabelService specialLabelService;
    @Resource
    private SpecialLabelValidator specialLabelValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(specialLabelValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SpecialLabelModel>> query(QuerySpecialLabelModel querySpecialLabelModel, Pageable pageable) {
        return new Result<>(specialLabelService.getPage(querySpecialLabelModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SpecialLabelModel> getInfo(@PathVariable Long id) {
        return new Result<>(specialLabelService.getById(id));
    }

    @PostMapping
    public Result<SpecialLabelModel> create(@Valid @RequestBody SpecialLabelModel specialLabelModel) {
        specialLabelService.add(specialLabelModel);
        return new Result<>(specialLabelService.getById(specialLabelModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SpecialLabelModel> update(@Valid @RequestBody SpecialLabelModel specialLabelModel) {
        specialLabelService.update(specialLabelModel);
        return new Result<>(specialLabelService.getById(specialLabelModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        specialLabelService.remove(id);
        return Result.SUCCESS;
    }
}
