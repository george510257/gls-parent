package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreTemplateModel;
import com.gls.quality.inspection.process.web.service.ScoreTemplateService;
import com.gls.quality.inspection.process.web.validator.ScoreTemplateValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/scoreTemplate")
public class ScoreTemplateController extends BaseController<ScoreTemplateService, ScoreTemplateModel, QueryScoreTemplateModel> {
    public ScoreTemplateController(ScoreTemplateService service, ScoreTemplateValidator validator) {
        super(service, validator);
    }
}
