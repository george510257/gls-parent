package com.gls.common.user.web.rpc;

import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.api.rpc.UserApi;
import com.gls.common.user.web.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author george
 */
@DubboService
public class UserRpcService implements UserApi {
    @Resource
    private UserService userService;

    @Override
    public UserModel updatePassword(UserModel user, String newPassword) {
        return userService.updatePassword(user, newPassword);
    }

    @Override
    public UserModel loadUserByUsername(String username) {
        return userService.loadUserByUsername(username);
    }
}
