package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.UserGroupingModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserGroupingModel;
import com.gls.quality.inspection.process.web.service.UserGroupingService;
import com.gls.quality.inspection.process.web.validator.UserGroupingValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/userGrouping")
public class UserGroupingController extends BaseController<UserGroupingService, UserGroupingModel, QueryUserGroupingModel> {
    public UserGroupingController(UserGroupingService service, UserGroupingValidator validator) {
        super(service, validator);
    }
}
