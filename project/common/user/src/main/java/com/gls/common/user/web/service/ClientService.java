package com.gls.common.user.web.service;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.api.model.query.QueryClientModel;
import com.gls.common.user.api.rpc.ClientApi;
import com.gls.starter.data.jpa.base.BaseService;

/**
 * @author george
 */
public interface ClientService extends BaseService<ClientModel, QueryClientModel>, ClientApi {
    /**
     * 保存默认客户端信息
     */
    void saveDefaultClient();
}
