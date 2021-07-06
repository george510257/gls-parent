package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordService;
import com.gls.quality.inspection.process.web.validator.ResultTextSplitWordValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/resultTextSplitWord")
public class ResultTextSplitWordController extends BaseController<ResultTextSplitWordService, ResultTextSplitWordModel, QueryResultTextSplitWordModel> {
    public ResultTextSplitWordController(ResultTextSplitWordService service, ResultTextSplitWordValidator validator) {
        super(service, validator);
    }
}
