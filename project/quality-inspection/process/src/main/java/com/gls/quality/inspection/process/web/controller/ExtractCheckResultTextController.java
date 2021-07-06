package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultTextModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultTextService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckResultTextValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/extractCheckResultText")
public class ExtractCheckResultTextController extends BaseController<ExtractCheckResultTextService, ExtractCheckResultTextModel, QueryExtractCheckResultTextModel> {
    public ExtractCheckResultTextController(ExtractCheckResultTextService service, ExtractCheckResultTextValidator validator) {
        super(service, validator);
    }
}
