package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import com.gls.quality.inspection.process.web.model.query.QueryBusinessLineModel;
import com.gls.quality.inspection.process.web.service.BusinessLineService;
import com.gls.quality.inspection.process.web.validator.BusinessLineValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/businessLine")
public class BusinessLineController extends BaseController<BusinessLineService, BusinessLineModel, QueryBusinessLineModel> {
    public BusinessLineController(BusinessLineService service, BusinessLineValidator validator) {
        super(service, validator);
    }
}
