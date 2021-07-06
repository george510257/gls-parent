package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckResultModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckResultService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckResultValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/extractCheckResult")
public class ExtractCheckResultController extends BaseController<ExtractCheckResultService, ExtractCheckResultModel, QueryExtractCheckResultModel> {
    public ExtractCheckResultController(ExtractCheckResultService service, ExtractCheckResultValidator validator) {
        super(service, validator);
    }
}
