package com.gls.demo.security.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.demo.security.web.model.UserModel;
import com.gls.demo.security.web.model.UserQueryConditionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping
    @JsonView(UserModel.UserSimpleView.class)
    public List<UserModel> query(UserQueryConditionModel condition,
                                 @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

        log.info("condition: " + condition.toString());

        log.info("PageSize: " + pageable.getPageSize());
        log.info("PageNumber: " + pageable.getPageNumber());
        log.info("Sort: " + pageable.getSort());

        List<UserModel> list = new ArrayList<>();
        list.add(new UserModel());
        list.add(new UserModel());
        list.add(new UserModel());
        log.info("length: " + list.size());
        return list;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(UserModel.UserDetailView.class)
    public UserModel getInfo(@PathVariable String id) {
        log.info("进入getInfo服务");
        UserModel userModel = new UserModel();
        userModel.setUsername("tom");
        return userModel;
    }

    @PostMapping
    public UserModel create(@Valid @RequestBody UserModel user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(objectError -> log.error(objectError.getDefaultMessage()));
        }

        log.info("id: {}", user.getId());
        log.info("username: {}", user.getUsername());
        log.info("password: {}", user.getPassword());
        log.info("birthday: {}", user.getBirthday());

        user.setId(1L);
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public UserModel update(@Valid @RequestBody UserModel user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = fieldError.getField() + ": " + fieldError.getDefaultMessage();
                log.error(message);
            });
        }

        log.info("id: {}", user.getId());
        log.info("username: {}", user.getUsername());
        log.info("password: {}", user.getPassword());
        log.info("birthday: {}", user.getBirthday());

        user.setId(1L);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        log.info("id: {}", id);
    }
}
