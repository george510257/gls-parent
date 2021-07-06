package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.service.SpotCheckDistributeService;
import com.gls.quality.inspection.process.web.validator.SpotCheckDistributeValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/spotCheckDistribute")
public class SpotCheckDistributeController extends BaseController<SpotCheckDistributeService, SpotCheckDistributeModel, QuerySpotCheckDistributeModel> {
    public SpotCheckDistributeController(SpotCheckDistributeService service, SpotCheckDistributeValidator validator) {
        super(service, validator);
    }
}
