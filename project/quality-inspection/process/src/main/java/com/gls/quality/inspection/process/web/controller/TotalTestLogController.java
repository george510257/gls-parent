package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestLogModel;
import com.gls.quality.inspection.process.web.service.TotalTestLogService;
import com.gls.quality.inspection.process.web.validator.TotalTestLogValidator;
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
@RequestMapping("/totalTestLog")
public class TotalTestLogController {
    @Resource
    private TotalTestLogService totalTestLogService;
    @Resource
    private TotalTestLogValidator totalTestLogValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(totalTestLogValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<TotalTestLogModel>> query(QueryTotalTestLogModel queryTotalTestLogModel, Pageable pageable) {
        return new Result<>(totalTestLogService.getPage(queryTotalTestLogModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<TotalTestLogModel> getInfo(@PathVariable Long id) {
        return new Result<>(totalTestLogService.getById(id));
    }

    @PostMapping
    public Result<TotalTestLogModel> create(@Valid @RequestBody TotalTestLogModel totalTestLogModel) {
        totalTestLogService.add(totalTestLogModel);
        return new Result<>(totalTestLogService.getById(totalTestLogModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<TotalTestLogModel> update(@Valid @RequestBody TotalTestLogModel totalTestLogModel) {
        totalTestLogService.update(totalTestLogModel);
        return new Result<>(totalTestLogService.getById(totalTestLogModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        totalTestLogService.remove(id);
        return Result.SUCCESS;
    }
}
