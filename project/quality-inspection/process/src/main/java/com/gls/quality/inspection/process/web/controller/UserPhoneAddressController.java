package com.gls.quality.inspection.process.web.controller;

import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserPhoneAddressModel;
import com.gls.quality.inspection.process.web.service.UserPhoneAddressService;
import com.gls.quality.inspection.process.web.validator.UserPhoneAddressValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/userPhoneAddress")
public class UserPhoneAddressController extends BaseController<UserPhoneAddressService, UserPhoneAddressModel, QueryUserPhoneAddressModel> {
    public UserPhoneAddressController(UserPhoneAddressService service, UserPhoneAddressValidator validator) {
        super(service, validator);
    }
}
