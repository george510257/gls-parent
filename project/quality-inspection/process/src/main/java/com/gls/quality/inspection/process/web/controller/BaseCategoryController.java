package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.BaseCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryBaseCategoryModel;
import com.gls.quality.inspection.process.web.service.BaseCategoryService;
import com.gls.quality.inspection.process.web.validator.BaseCategoryValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/baseCategory")
public class BaseCategoryController extends BaseController<BaseCategoryService, BaseCategoryModel, QueryBaseCategoryModel> {
    public BaseCategoryController(BaseCategoryService service, BaseCategoryValidator validator) {
        super(service, validator);
    }
}
