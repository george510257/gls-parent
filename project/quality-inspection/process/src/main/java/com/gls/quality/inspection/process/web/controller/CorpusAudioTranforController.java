package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.service.CorpusAudioTranforService;
import com.gls.quality.inspection.process.web.validator.CorpusAudioTranforValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/corpusAudioTranfor")
public class CorpusAudioTranforController extends BaseController<CorpusAudioTranforService, CorpusAudioTranforModel, QueryCorpusAudioTranforModel> {
    public CorpusAudioTranforController(CorpusAudioTranforService service, CorpusAudioTranforValidator validator) {
        super(service, validator);
    }
}
