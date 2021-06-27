package com.gls.common.user.web.controller;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.support.LoginUserHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author george
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private LoginUserHolder loginUserHolder;

    @GetMapping("/loginUser")
    public UserModel loginUser() {
        return loginUserHolder.getCurrentUser();
    }
}
