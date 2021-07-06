package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.service.ConfigInvalidAudioService;
import com.gls.quality.inspection.process.web.validator.ConfigInvalidAudioValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/configInvalidAudio")
public class ConfigInvalidAudioController extends BaseController<ConfigInvalidAudioService, ConfigInvalidAudioModel, QueryConfigInvalidAudioModel> {
    public ConfigInvalidAudioController(ConfigInvalidAudioService service, ConfigInvalidAudioValidator validator) {
        super(service, validator);
    }
}
