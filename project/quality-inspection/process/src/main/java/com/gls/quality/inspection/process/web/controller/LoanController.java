package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.LoanModel;
import com.gls.quality.inspection.process.web.model.query.QueryLoanModel;
import com.gls.quality.inspection.process.web.service.LoanService;
import com.gls.quality.inspection.process.web.validator.LoanValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/loan")
public class LoanController extends BaseController<LoanService, LoanModel, QueryLoanModel> {
    public LoanController(LoanService service, LoanValidator validator) {
        super(service, validator);
    }
}
