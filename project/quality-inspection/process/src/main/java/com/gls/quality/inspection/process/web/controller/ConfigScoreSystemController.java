package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.service.ConfigScoreSystemService;
import com.gls.quality.inspection.process.web.validator.ConfigScoreSystemValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/configScoreSystem")
public class ConfigScoreSystemController extends BaseController<ConfigScoreSystemService, ConfigScoreSystemModel, QueryConfigScoreSystemModel> {
    public ConfigScoreSystemController(ConfigScoreSystemService service, ConfigScoreSystemValidator validator) {
        super(service, validator);
    }
}
