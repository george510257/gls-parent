package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CompanyModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyModel;
import com.gls.quality.inspection.process.web.service.CompanyService;
import com.gls.quality.inspection.process.web.validator.CompanyValidator;
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
@RequestMapping("/company")
public class CompanyController {
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyValidator companyValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(companyValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CompanyModel>> query(QueryCompanyModel queryCompanyModel, Pageable pageable) {
        return new Result<>(companyService.getPage(queryCompanyModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CompanyModel> getInfo(@PathVariable Long id) {
        return new Result<>(companyService.getById(id));
    }

    @PostMapping
    public Result<CompanyModel> create(@Valid @RequestBody CompanyModel companyModel) {
        companyService.add(companyModel);
        return new Result<>(companyService.getById(companyModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CompanyModel> update(@Valid @RequestBody CompanyModel companyModel) {
        companyService.update(companyModel);
        return new Result<>(companyService.getById(companyModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        companyService.remove(id);
        return Result.SUCCESS;
    }
}
