package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import com.gls.quality.inspection.process.web.model.query.QueryOperateLogDetailsModel;
import com.gls.quality.inspection.process.web.service.OperateLogDetailsService;
import com.gls.quality.inspection.process.web.validator.OperateLogDetailsValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/operateLogDetails")
public class OperateLogDetailsController extends BaseController<OperateLogDetailsService, OperateLogDetailsModel, QueryOperateLogDetailsModel> {
    public OperateLogDetailsController(OperateLogDetailsService service, OperateLogDetailsValidator validator) {
        super(service, validator);
    }
}
