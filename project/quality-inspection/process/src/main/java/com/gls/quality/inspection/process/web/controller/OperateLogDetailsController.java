package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import com.gls.quality.inspection.process.web.model.query.QueryOperateLogDetailsModel;
import com.gls.quality.inspection.process.web.service.OperateLogDetailsService;
import com.gls.quality.inspection.process.web.validator.OperateLogDetailsValidator;
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
@RequestMapping("/operateLogDetails")
public class OperateLogDetailsController {
    @Resource
    private OperateLogDetailsService operateLogDetailsService;
    @Resource
    private OperateLogDetailsValidator operateLogDetailsValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(operateLogDetailsValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<OperateLogDetailsModel>> query(QueryOperateLogDetailsModel queryOperateLogDetailsModel, Pageable pageable) {
        return new Result<>(operateLogDetailsService.getPage(queryOperateLogDetailsModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<OperateLogDetailsModel> getInfo(@PathVariable Long id) {
        return new Result<>(operateLogDetailsService.getById(id));
    }

    @PostMapping
    public Result<OperateLogDetailsModel> create(@Valid @RequestBody OperateLogDetailsModel operateLogDetailsModel) {
        operateLogDetailsService.add(operateLogDetailsModel);
        return new Result<>(operateLogDetailsService.getById(operateLogDetailsModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<OperateLogDetailsModel> update(@Valid @RequestBody OperateLogDetailsModel operateLogDetailsModel) {
        operateLogDetailsService.update(operateLogDetailsModel);
        return new Result<>(operateLogDetailsService.getById(operateLogDetailsModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        operateLogDetailsService.remove(id);
        return Result.SUCCESS;
    }
}
