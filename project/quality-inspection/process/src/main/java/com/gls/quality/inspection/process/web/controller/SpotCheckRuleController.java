package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckRuleModel;
import com.gls.quality.inspection.process.web.service.SpotCheckRuleService;
import com.gls.quality.inspection.process.web.validator.SpotCheckRuleValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/spotCheckRule")
public class SpotCheckRuleController extends BaseController<SpotCheckRuleService, SpotCheckRuleModel, QuerySpotCheckRuleModel> {
    public SpotCheckRuleController(SpotCheckRuleService service, SpotCheckRuleValidator validator) {
        super(service, validator);
    }
}
