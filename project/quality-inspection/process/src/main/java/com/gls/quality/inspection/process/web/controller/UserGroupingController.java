package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.UserGroupingModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserGroupingModel;
import com.gls.quality.inspection.process.web.service.UserGroupingService;
import com.gls.quality.inspection.process.web.validator.UserGroupingValidator;
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
@RequestMapping("/userGrouping")
public class UserGroupingController {
    @Resource
    private UserGroupingService userGroupingService;
    @Resource
    private UserGroupingValidator userGroupingValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userGroupingValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<UserGroupingModel>> query(QueryUserGroupingModel queryUserGroupingModel, Pageable pageable) {
        return new Result<>(userGroupingService.getPage(queryUserGroupingModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<UserGroupingModel> getInfo(@PathVariable Long id) {
        return new Result<>(userGroupingService.getById(id));
    }

    @PostMapping
    public Result<UserGroupingModel> create(@Valid @RequestBody UserGroupingModel userGroupingModel) {
        userGroupingService.add(userGroupingModel);
        return new Result<>(userGroupingService.getById(userGroupingModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<UserGroupingModel> update(@Valid @RequestBody UserGroupingModel userGroupingModel) {
        userGroupingService.update(userGroupingModel);
        return new Result<>(userGroupingService.getById(userGroupingModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        userGroupingService.remove(id);
        return Result.SUCCESS;
    }
}
