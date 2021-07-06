package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import com.gls.quality.inspection.process.web.model.query.QueryTotalTestLogModel;
import com.gls.quality.inspection.process.web.service.TotalTestLogService;
import com.gls.quality.inspection.process.web.validator.TotalTestLogValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/totalTestLog")
public class TotalTestLogController extends BaseController<TotalTestLogService, TotalTestLogModel, QueryTotalTestLogModel> {
    public TotalTestLogController(TotalTestLogService service, TotalTestLogValidator validator) {
        super(service, validator);
    }
}
