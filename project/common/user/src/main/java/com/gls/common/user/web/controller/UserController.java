package com.gls.common.user.web.controller;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.api.model.query.QueryUserModel;
import com.gls.common.user.support.LoginUserHolder;
import com.gls.common.user.web.service.UserService;
import com.gls.common.user.web.validator.UserValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService, UserModel, QueryUserModel> {
    @Resource
    private LoginUserHolder loginUserHolder;

    public UserController(UserService service, UserValidator validator) {
        super(service, validator);
    }

    @GetMapping("/loginUser")
    public UserModel loginUser() {
        return loginUserHolder.getCurrentUser();
    }
}
