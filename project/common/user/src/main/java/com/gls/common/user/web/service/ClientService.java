package com.gls.common.user.web.service;

import com.gls.common.user.api.rpc.ClientApi;

/**
 * @author george
 */
public interface ClientService extends ClientApi {

    /**
     * 保存默认客户端信息
     */
    void saveDefaultClient();
}
