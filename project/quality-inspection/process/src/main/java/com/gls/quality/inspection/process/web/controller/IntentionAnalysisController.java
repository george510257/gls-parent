package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import com.gls.quality.inspection.process.web.model.query.QueryIntentionAnalysisModel;
import com.gls.quality.inspection.process.web.service.IntentionAnalysisService;
import com.gls.quality.inspection.process.web.validator.IntentionAnalysisValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/intentionAnalysis")
public class IntentionAnalysisController extends BaseController<IntentionAnalysisService, IntentionAnalysisModel, QueryIntentionAnalysisModel> {
    public IntentionAnalysisController(IntentionAnalysisService service, IntentionAnalysisValidator validator) {
        super(service, validator);
    }
}
