package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryCombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.service.CombinedScoreItemsService;
import com.gls.quality.inspection.process.web.validator.CombinedScoreItemsValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/combinedScoreItems")
public class CombinedScoreItemsController extends BaseController<CombinedScoreItemsService, CombinedScoreItemsModel, QueryCombinedScoreItemsModel> {
    public CombinedScoreItemsController(CombinedScoreItemsService service, CombinedScoreItemsValidator validator) {
        super(service, validator);
    }
}
