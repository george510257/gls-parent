package com.gls.common.user.web.controller;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.api.model.query.QueryClientModel;
import com.gls.common.user.web.service.ClientService;
import com.gls.common.user.web.validator.ClientValidator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController extends BaseController<ClientService, ClientModel, QueryClientModel> {
    public ClientController(ClientService service, ClientValidator validator) {
        super(service, validator);
    }
}
