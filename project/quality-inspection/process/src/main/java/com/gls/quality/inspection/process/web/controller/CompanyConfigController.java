package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CompanyConfigModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyConfigModel;
import com.gls.quality.inspection.process.web.service.CompanyConfigService;
import com.gls.quality.inspection.process.web.validator.CompanyConfigValidator;
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
@RequestMapping("/companyConfig")
public class CompanyConfigController {
    @Resource
    private CompanyConfigService companyConfigService;
    @Resource
    private CompanyConfigValidator companyConfigValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(companyConfigValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CompanyConfigModel>> query(QueryCompanyConfigModel queryCompanyConfigModel, Pageable pageable) {
        return new Result<>(companyConfigService.getPage(queryCompanyConfigModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CompanyConfigModel> getInfo(@PathVariable Long id) {
        return new Result<>(companyConfigService.getById(id));
    }

    @PostMapping
    public Result<CompanyConfigModel> create(@Valid @RequestBody CompanyConfigModel companyConfigModel) {
        companyConfigService.add(companyConfigModel);
        return new Result<>(companyConfigService.getById(companyConfigModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CompanyConfigModel> update(@Valid @RequestBody CompanyConfigModel companyConfigModel) {
        companyConfigService.update(companyConfigModel);
        return new Result<>(companyConfigService.getById(companyConfigModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        companyConfigService.remove(id);
        return Result.SUCCESS;
    }
}
