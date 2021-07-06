package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CompanyConfigModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyConfigModel;
import com.gls.quality.inspection.process.web.service.CompanyConfigService;
import com.gls.quality.inspection.process.web.validator.CompanyConfigValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/companyConfig")
public class CompanyConfigController extends BaseController<CompanyConfigService, CompanyConfigModel, QueryCompanyConfigModel> {
    public CompanyConfigController(CompanyConfigService service, CompanyConfigValidator validator) {
        super(service, validator);
    }
}
