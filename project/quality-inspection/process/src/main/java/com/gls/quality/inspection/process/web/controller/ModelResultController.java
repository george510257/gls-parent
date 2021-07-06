package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ModelResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryModelResultModel;
import com.gls.quality.inspection.process.web.service.ModelResultService;
import com.gls.quality.inspection.process.web.validator.ModelResultValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/modelResult")
public class ModelResultController extends BaseController<ModelResultService, ModelResultModel, QueryModelResultModel> {
    public ModelResultController(ModelResultService service, ModelResultValidator validator) {
        super(service, validator);
    }
}
