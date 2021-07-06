package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigWordFilterModel;
import com.gls.quality.inspection.process.web.service.ConfigWordFilterService;
import com.gls.quality.inspection.process.web.validator.ConfigWordFilterValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/configWordFilter")
public class ConfigWordFilterController extends BaseController<ConfigWordFilterService, ConfigWordFilterModel, QueryConfigWordFilterModel> {
    public ConfigWordFilterController(ConfigWordFilterService service, ConfigWordFilterValidator validator) {
        super(service, validator);
    }
}
