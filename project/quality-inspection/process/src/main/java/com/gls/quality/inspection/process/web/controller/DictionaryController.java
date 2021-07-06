package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.DictionaryModel;
import com.gls.quality.inspection.process.web.model.query.QueryDictionaryModel;
import com.gls.quality.inspection.process.web.service.DictionaryService;
import com.gls.quality.inspection.process.web.validator.DictionaryValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController<DictionaryService, DictionaryModel, QueryDictionaryModel> {
    public DictionaryController(DictionaryService service, DictionaryValidator validator) {
        super(service, validator);
    }
}
