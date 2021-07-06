package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.LabelModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelModel;
import com.gls.quality.inspection.process.web.service.LabelService;
import com.gls.quality.inspection.process.web.validator.LabelValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/label")
public class LabelController extends BaseController<LabelService, LabelModel, QueryLabelModel> {
    public LabelController(LabelService service, LabelValidator validator) {
        super(service, validator);
    }
}
