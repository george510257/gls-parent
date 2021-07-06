package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckAudioValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/extractCheckAudio")
public class ExtractCheckAudioController extends BaseController<ExtractCheckAudioService, ExtractCheckAudioModel, QueryExtractCheckAudioModel> {
    public ExtractCheckAudioController(ExtractCheckAudioService service, ExtractCheckAudioValidator validator) {
        super(service, validator);
    }
}
