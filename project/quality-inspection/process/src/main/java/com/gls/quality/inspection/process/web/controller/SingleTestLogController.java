package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QuerySingleTestLogModel;
import com.gls.quality.inspection.process.web.service.SingleTestLogService;
import com.gls.quality.inspection.process.web.validator.SingleTestLogValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/singleTestLog")
public class SingleTestLogController extends BaseController<SingleTestLogService, SingleTestLogModel, QuerySingleTestLogModel> {
    public SingleTestLogController(SingleTestLogService service, SingleTestLogValidator validator) {
        super(service, validator);
    }
}
