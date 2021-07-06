package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordCustomerModel;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordCustomerService;
import com.gls.quality.inspection.process.web.validator.ResultTextSplitWordCustomerValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/resultTextSplitWordCustomer")
public class ResultTextSplitWordCustomerController extends BaseController<ResultTextSplitWordCustomerService, ResultTextSplitWordCustomerModel, QueryResultTextSplitWordCustomerModel> {
    public ResultTextSplitWordCustomerController(ResultTextSplitWordCustomerService service, ResultTextSplitWordCustomerValidator validator) {
        super(service, validator);
    }
}
