package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ModelModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelModel;
import com.gls.quality.inspection.process.web.service.ModelService;
import com.gls.quality.inspection.process.web.validator.ModelValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/model")
public class ModelController extends BaseController<ModelService, ModelModel, QueryModelModel> {
    public ModelController(ModelService service, ModelValidator validator) {
        super(service, validator);
    }
}
