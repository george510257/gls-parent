package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/extractCheck")
public class ExtractCheckController extends BaseController<ExtractCheckService, ExtractCheckModel, QueryExtractCheckModel> {
    public ExtractCheckController(ExtractCheckService service, ExtractCheckValidator validator) {
        super(service, validator);
    }
}
