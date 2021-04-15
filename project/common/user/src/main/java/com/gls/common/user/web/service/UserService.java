package com.gls.common.user.web.service;

import com.gls.common.user.api.rpc.UserApi;

/**
 * @author george
 */
public interface UserService extends UserApi {

    /**
     * 保存默认用户信息
     */
    void saveDefaultUser();
}
