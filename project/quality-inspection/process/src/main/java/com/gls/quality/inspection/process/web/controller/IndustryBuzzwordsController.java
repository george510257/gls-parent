package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.service.IndustryBuzzwordsService;
import com.gls.quality.inspection.process.web.validator.IndustryBuzzwordsValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/industryBuzzwords")
public class IndustryBuzzwordsController extends BaseController<IndustryBuzzwordsService, IndustryBuzzwordsModel, QueryIndustryBuzzwordsModel> {
    public IndustryBuzzwordsController(IndustryBuzzwordsService service, IndustryBuzzwordsValidator validator) {
        super(service, validator);
    }
}
