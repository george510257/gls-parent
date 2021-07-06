package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.model.query.QueryRecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.service.RecommendExtQuestionService;
import com.gls.quality.inspection.process.web.validator.RecommendExtQuestionValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/recommendExtQuestion")
public class RecommendExtQuestionController extends BaseController<RecommendExtQuestionService, RecommendExtQuestionModel, QueryRecommendExtQuestionModel> {
    public RecommendExtQuestionController(RecommendExtQuestionService service, RecommendExtQuestionValidator validator) {
        super(service, validator);
    }
}
