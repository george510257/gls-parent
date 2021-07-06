package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QuerySingleTestLogModel;
import com.gls.quality.inspection.process.web.service.SingleTestLogService;
import com.gls.quality.inspection.process.web.validator.SingleTestLogValidator;
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
@RequestMapping("/singleTestLog")
public class SingleTestLogController {
    @Resource
    private SingleTestLogService singleTestLogService;
    @Resource
    private SingleTestLogValidator singleTestLogValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(singleTestLogValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SingleTestLogModel>> query(QuerySingleTestLogModel querySingleTestLogModel, Pageable pageable) {
        return new Result<>(singleTestLogService.getPage(querySingleTestLogModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SingleTestLogModel> getInfo(@PathVariable Long id) {
        return new Result<>(singleTestLogService.getById(id));
    }

    @PostMapping
    public Result<SingleTestLogModel> create(@Valid @RequestBody SingleTestLogModel singleTestLogModel) {
        singleTestLogService.add(singleTestLogModel);
        return new Result<>(singleTestLogService.getById(singleTestLogModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SingleTestLogModel> update(@Valid @RequestBody SingleTestLogModel singleTestLogModel) {
        singleTestLogService.update(singleTestLogModel);
        return new Result<>(singleTestLogService.getById(singleTestLogModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        singleTestLogService.remove(id);
        return Result.SUCCESS;
    }
}
