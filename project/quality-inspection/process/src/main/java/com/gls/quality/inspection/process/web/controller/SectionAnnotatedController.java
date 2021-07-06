package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SectionAnnotatedModel;
import com.gls.quality.inspection.process.web.model.query.QuerySectionAnnotatedModel;
import com.gls.quality.inspection.process.web.service.SectionAnnotatedService;
import com.gls.quality.inspection.process.web.validator.SectionAnnotatedValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/sectionAnnotated")
public class SectionAnnotatedController extends BaseController<SectionAnnotatedService, SectionAnnotatedModel, QuerySectionAnnotatedModel> {
    public SectionAnnotatedController(SectionAnnotatedService service, SectionAnnotatedValidator validator) {
        super(service, validator);
    }
}
