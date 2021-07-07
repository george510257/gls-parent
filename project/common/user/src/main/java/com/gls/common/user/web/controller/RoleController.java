package com.gls.common.user.web.controller;

import com.gls.common.user.api.model.RoleModel;
import com.gls.common.user.api.model.query.QueryRoleModel;
import com.gls.common.user.web.service.RoleService;
import com.gls.common.user.web.validator.RoleValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleService, RoleModel, QueryRoleModel> {
    public RoleController(RoleService service, RoleValidator validator) {
        super(service, validator);
    }
}
