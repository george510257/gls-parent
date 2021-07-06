package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserPhoneAddressModel;
import com.gls.quality.inspection.process.web.service.UserPhoneAddressService;
import com.gls.quality.inspection.process.web.validator.UserPhoneAddressValidator;
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
@RequestMapping("/userPhoneAddress")
public class UserPhoneAddressController {
    @Resource
    private UserPhoneAddressService userPhoneAddressService;
    @Resource
    private UserPhoneAddressValidator userPhoneAddressValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userPhoneAddressValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<UserPhoneAddressModel>> query(QueryUserPhoneAddressModel queryUserPhoneAddressModel, Pageable pageable) {
        return new Result<>(userPhoneAddressService.getPage(queryUserPhoneAddressModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<UserPhoneAddressModel> getInfo(@PathVariable Long id) {
        return new Result<>(userPhoneAddressService.getById(id));
    }

    @PostMapping
    public Result<UserPhoneAddressModel> create(@Valid @RequestBody UserPhoneAddressModel userPhoneAddressModel) {
        userPhoneAddressService.add(userPhoneAddressModel);
        return new Result<>(userPhoneAddressService.getById(userPhoneAddressModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<UserPhoneAddressModel> update(@Valid @RequestBody UserPhoneAddressModel userPhoneAddressModel) {
        userPhoneAddressService.update(userPhoneAddressModel);
        return new Result<>(userPhoneAddressService.getById(userPhoneAddressModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        userPhoneAddressService.remove(id);
        return Result.SUCCESS;
    }
}
