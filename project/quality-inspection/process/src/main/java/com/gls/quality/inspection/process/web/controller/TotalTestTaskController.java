package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestTaskModel;
import com.gls.quality.inspection.process.web.service.TotalTestTaskService;
import com.gls.quality.inspection.process.web.validator.TotalTestTaskValidator;
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
@RequestMapping("/totalTestTask")
public class TotalTestTaskController {
    @Resource
    private TotalTestTaskService totalTestTaskService;
    @Resource
    private TotalTestTaskValidator totalTestTaskValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(totalTestTaskValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<TotalTestTaskModel>> query(QueryTotalTestTaskModel queryTotalTestTaskModel, Pageable pageable) {
        return new Result<>(totalTestTaskService.getPage(queryTotalTestTaskModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<TotalTestTaskModel> getInfo(@PathVariable Long id) {
        return new Result<>(totalTestTaskService.getById(id));
    }

    @PostMapping
    public Result<TotalTestTaskModel> create(@Valid @RequestBody TotalTestTaskModel totalTestTaskModel) {
        totalTestTaskService.add(totalTestTaskModel);
        return new Result<>(totalTestTaskService.getById(totalTestTaskModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<TotalTestTaskModel> update(@Valid @RequestBody TotalTestTaskModel totalTestTaskModel) {
        totalTestTaskService.update(totalTestTaskModel);
        return new Result<>(totalTestTaskService.getById(totalTestTaskModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        totalTestTaskService.remove(id);
        return Result.SUCCESS;
    }
}
