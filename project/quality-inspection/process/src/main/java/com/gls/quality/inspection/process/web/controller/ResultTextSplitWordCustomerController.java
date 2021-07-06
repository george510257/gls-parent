package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordCustomerService;
import com.gls.quality.inspection.process.web.validator.ResultTextSplitWordCustomerValidator;
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
@RequestMapping("/resultTextSplitWordCustomer")
public class ResultTextSplitWordCustomerController {
    @Resource
    private ResultTextSplitWordCustomerService resultTextSplitWordCustomerService;
    @Resource
    private ResultTextSplitWordCustomerValidator resultTextSplitWordCustomerValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(resultTextSplitWordCustomerValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ResultTextSplitWordCustomerModel>> query(QueryResultTextSplitWordCustomerModel queryResultTextSplitWordCustomerModel, Pageable pageable) {
        return new Result<>(resultTextSplitWordCustomerService.getPage(queryResultTextSplitWordCustomerModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ResultTextSplitWordCustomerModel> getInfo(@PathVariable Long id) {
        return new Result<>(resultTextSplitWordCustomerService.getById(id));
    }

    @PostMapping
    public Result<ResultTextSplitWordCustomerModel> create(@Valid @RequestBody ResultTextSplitWordCustomerModel resultTextSplitWordCustomerModel) {
        resultTextSplitWordCustomerService.add(resultTextSplitWordCustomerModel);
        return new Result<>(resultTextSplitWordCustomerService.getById(resultTextSplitWordCustomerModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ResultTextSplitWordCustomerModel> update(@Valid @RequestBody ResultTextSplitWordCustomerModel resultTextSplitWordCustomerModel) {
        resultTextSplitWordCustomerService.update(resultTextSplitWordCustomerModel);
        return new Result<>(resultTextSplitWordCustomerService.getById(resultTextSplitWordCustomerModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        resultTextSplitWordCustomerService.remove(id);
        return Result.SUCCESS;
    }
}
