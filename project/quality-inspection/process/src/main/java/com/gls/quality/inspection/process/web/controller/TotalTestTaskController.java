package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestTaskModel;
import com.gls.quality.inspection.process.web.service.TotalTestTaskService;
import com.gls.quality.inspection.process.web.validator.TotalTestTaskValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/totalTestTask")
public class TotalTestTaskController extends BaseController<TotalTestTaskService, TotalTestTaskModel, QueryTotalTestTaskModel> {
    public TotalTestTaskController(TotalTestTaskService service, TotalTestTaskValidator validator) {
        super(service, validator);
    }
}
