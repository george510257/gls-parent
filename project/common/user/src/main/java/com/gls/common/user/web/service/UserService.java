package com.gls.common.user.web.service;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.api.model.query.QueryUserModel;
import com.gls.common.user.api.rpc.UserApi;
import com.gls.starter.data.jpa.base.BaseService;

/**
 * @author george
 */
public interface UserService extends BaseService<UserModel, QueryUserModel>, UserApi {
    /**
     * 保存默认用户信息
     */
    void saveDefaultUser();
}
