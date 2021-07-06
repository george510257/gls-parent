package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpecialLabelModel;
import com.gls.quality.inspection.process.web.service.SpecialLabelService;
import com.gls.quality.inspection.process.web.validator.SpecialLabelValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/specialLabel")
public class SpecialLabelController extends BaseController<SpecialLabelService, SpecialLabelModel, QuerySpecialLabelModel> {
    public SpecialLabelController(SpecialLabelService service, SpecialLabelValidator validator) {
        super(service, validator);
    }
}
