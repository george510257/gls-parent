package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CorpusModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusModel;
import com.gls.quality.inspection.process.web.service.CorpusService;
import com.gls.quality.inspection.process.web.validator.CorpusValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/corpus")
public class CorpusController extends BaseController<CorpusService, CorpusModel, QueryCorpusModel> {
    public CorpusController(CorpusService service, CorpusValidator validator) {
        super(service, validator);
    }
}
