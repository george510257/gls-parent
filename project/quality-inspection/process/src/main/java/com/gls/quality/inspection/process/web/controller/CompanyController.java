package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CompanyModel;
import com.gls.quality.inspection.process.web.model.query.QueryCompanyModel;
import com.gls.quality.inspection.process.web.service.CompanyService;
import com.gls.quality.inspection.process.web.validator.CompanyValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<CompanyService, CompanyModel, QueryCompanyModel> {
    public CompanyController(CompanyService service, CompanyValidator validator) {
        super(service, validator);
    }
}
