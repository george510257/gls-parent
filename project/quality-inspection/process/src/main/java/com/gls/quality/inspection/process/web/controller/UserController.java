package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.UserModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserModel;
import com.gls.quality.inspection.process.web.service.UserService;
import com.gls.quality.inspection.process.web.validator.UserValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService, UserModel, QueryUserModel> {
    public UserController(UserService service, UserValidator validator) {
        super(service, validator);
    }
}
