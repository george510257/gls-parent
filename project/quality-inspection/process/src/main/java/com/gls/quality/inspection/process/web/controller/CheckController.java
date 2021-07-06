package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryCheckModel;
import com.gls.quality.inspection.process.web.service.CheckService;
import com.gls.quality.inspection.process.web.validator.CheckValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/check")
public class CheckController extends BaseController<CheckService, CheckModel, QueryCheckModel> {
    public CheckController(CheckService service, CheckValidator validator) {
        super(service, validator);
    }
}
