package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckModel;
import com.gls.quality.inspection.process.web.service.SpotCheckService;
import com.gls.quality.inspection.process.web.validator.SpotCheckValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/spotCheck")
public class SpotCheckController extends BaseController<SpotCheckService, SpotCheckModel, QuerySpotCheckModel> {
    public SpotCheckController(SpotCheckService service, SpotCheckValidator validator) {
        super(service, validator);
    }
}
