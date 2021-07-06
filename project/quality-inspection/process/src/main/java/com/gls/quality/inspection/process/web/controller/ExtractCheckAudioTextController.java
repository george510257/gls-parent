package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioTextService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckAudioTextValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/extractCheckAudioText")
public class ExtractCheckAudioTextController extends BaseController<ExtractCheckAudioTextService, ExtractCheckAudioTextModel, QueryExtractCheckAudioTextModel> {
    public ExtractCheckAudioTextController(ExtractCheckAudioTextService service, ExtractCheckAudioTextValidator validator) {
        super(service, validator);
    }
}
