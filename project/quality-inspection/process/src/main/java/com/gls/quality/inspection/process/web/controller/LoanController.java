package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.LoanModel;
import com.gls.quality.inspection.process.web.model.query.QueryLoanModel;
import com.gls.quality.inspection.process.web.service.LoanService;
import com.gls.quality.inspection.process.web.validator.LoanValidator;
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
@RequestMapping("/loan")
public class LoanController {
    @Resource
    private LoanService loanService;
    @Resource
    private LoanValidator loanValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(loanValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<LoanModel>> query(QueryLoanModel queryLoanModel, Pageable pageable) {
        return new Result<>(loanService.getPage(queryLoanModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<LoanModel> getInfo(@PathVariable Long id) {
        return new Result<>(loanService.getById(id));
    }

    @PostMapping
    public Result<LoanModel> create(@Valid @RequestBody LoanModel loanModel) {
        loanService.add(loanModel);
        return new Result<>(loanService.getById(loanModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<LoanModel> update(@Valid @RequestBody LoanModel loanModel) {
        loanService.update(loanModel);
        return new Result<>(loanService.getById(loanModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        loanService.remove(id);
        return Result.SUCCESS;
    }
}
