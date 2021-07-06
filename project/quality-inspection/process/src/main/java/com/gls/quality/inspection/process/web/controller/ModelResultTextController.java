package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultTextModel;
import com.gls.quality.inspection.process.web.service.ModelResultTextService;
import com.gls.quality.inspection.process.web.validator.ModelResultTextValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/modelResultText")
public class ModelResultTextController extends BaseController<ModelResultTextService, ModelResultTextModel, QueryModelResultTextModel> {
    public ModelResultTextController(ModelResultTextService service, ModelResultTextValidator validator) {
        super(service, validator);
    }
}
