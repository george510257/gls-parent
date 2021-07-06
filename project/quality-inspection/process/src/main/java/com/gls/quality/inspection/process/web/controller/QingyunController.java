package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.QingyunModel;
import com.gls.quality.inspection.process.web.model.query.QueryQingyunModel;
import com.gls.quality.inspection.process.web.service.QingyunService;
import com.gls.quality.inspection.process.web.validator.QingyunValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/qingyun")
public class QingyunController extends BaseController<QingyunService, QingyunModel, QueryQingyunModel> {
    public QingyunController(QingyunService service, QingyunValidator validator) {
        super(service, validator);
    }
}
