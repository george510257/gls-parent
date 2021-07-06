package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import com.gls.quality.inspection.process.web.model.query.QueryLabelTestDataModel;
import com.gls.quality.inspection.process.web.service.LabelTestDataService;
import com.gls.quality.inspection.process.web.validator.LabelTestDataValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/labelTestData")
public class LabelTestDataController extends BaseController<LabelTestDataService, LabelTestDataModel, QueryLabelTestDataModel> {
    public LabelTestDataController(LabelTestDataService service, LabelTestDataValidator validator) {
        super(service, validator);
    }
}
