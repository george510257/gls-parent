package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreItemsModel;
import com.gls.quality.inspection.process.web.service.ScoreItemsService;
import com.gls.quality.inspection.process.web.validator.ScoreItemsValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/scoreItems")
public class ScoreItemsController extends BaseController<ScoreItemsService, ScoreItemsModel, QueryScoreItemsModel> {
    public ScoreItemsController(ScoreItemsService service, ScoreItemsValidator validator) {
        super(service, validator);
    }
}
