package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryCategoryModel;
import com.gls.quality.inspection.process.web.service.IndustryCategoryService;
import com.gls.quality.inspection.process.web.validator.IndustryCategoryValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/industryCategory")
public class IndustryCategoryController extends BaseController<IndustryCategoryService, IndustryCategoryModel, QueryIndustryCategoryModel> {
    public IndustryCategoryController(IndustryCategoryService service, IndustryCategoryValidator validator) {
        super(service, validator);
    }
}
