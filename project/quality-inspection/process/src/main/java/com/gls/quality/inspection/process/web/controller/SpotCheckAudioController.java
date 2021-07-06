package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckAudioModel;
import com.gls.quality.inspection.process.web.service.SpotCheckAudioService;
import com.gls.quality.inspection.process.web.validator.SpotCheckAudioValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/spotCheckAudio")
public class SpotCheckAudioController extends BaseController<SpotCheckAudioService, SpotCheckAudioModel, QuerySpotCheckAudioModel> {
    public SpotCheckAudioController(SpotCheckAudioService service, SpotCheckAudioValidator validator) {
        super(service, validator);
    }
}
