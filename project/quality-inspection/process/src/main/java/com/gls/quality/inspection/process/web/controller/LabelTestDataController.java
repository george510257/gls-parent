package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelTestDataModel;
import com.gls.quality.inspection.process.web.service.LabelTestDataService;
import com.gls.quality.inspection.process.web.validator.LabelTestDataValidator;
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
@RequestMapping("/labelTestData")
public class LabelTestDataController {
    @Resource
    private LabelTestDataService labelTestDataService;
    @Resource
    private LabelTestDataValidator labelTestDataValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(labelTestDataValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<LabelTestDataModel>> query(QueryLabelTestDataModel queryLabelTestDataModel, Pageable pageable) {
        return new Result<>(labelTestDataService.getPage(queryLabelTestDataModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<LabelTestDataModel> getInfo(@PathVariable Long id) {
        return new Result<>(labelTestDataService.getById(id));
    }

    @PostMapping
    public Result<LabelTestDataModel> create(@Valid @RequestBody LabelTestDataModel labelTestDataModel) {
        labelTestDataService.add(labelTestDataModel);
        return new Result<>(labelTestDataService.getById(labelTestDataModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<LabelTestDataModel> update(@Valid @RequestBody LabelTestDataModel labelTestDataModel) {
        labelTestDataService.update(labelTestDataModel);
        return new Result<>(labelTestDataService.getById(labelTestDataModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        labelTestDataService.remove(id);
        return Result.SUCCESS;
    }
}
