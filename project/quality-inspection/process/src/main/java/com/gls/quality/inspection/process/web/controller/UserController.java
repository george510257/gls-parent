package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.UserModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserModel;
import com.gls.quality.inspection.process.web.service.UserService;
import com.gls.quality.inspection.process.web.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<UserModel>> query(QueryUserModel queryUserModel, Pageable pageable) {
        return new Result<>(userService.getPage(queryUserModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<UserModel> getInfo(@PathVariable Long id) {
        return new Result<>(userService.getById(id));
    }

    @PostMapping
    public Result<UserModel> create(@Valid @RequestBody UserModel userModel) {
        userService.add(userModel);
        return new Result<>(userService.getById(userModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<UserModel> update(@Valid @RequestBody UserModel userModel) {
        userService.update(userModel);
        return new Result<>(userService.getById(userModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        userService.remove(id);
        return Result.SUCCESS;
    }
}
