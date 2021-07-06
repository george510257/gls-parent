package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ConfigChannelModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigChannelModel;
import com.gls.quality.inspection.process.web.service.ConfigChannelService;
import com.gls.quality.inspection.process.web.validator.ConfigChannelValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/configChannel")
public class ConfigChannelController extends BaseController<ConfigChannelService, ConfigChannelModel, QueryConfigChannelModel> {
    public ConfigChannelController(ConfigChannelService service, ConfigChannelValidator validator) {
        super(service, validator);
    }
}
