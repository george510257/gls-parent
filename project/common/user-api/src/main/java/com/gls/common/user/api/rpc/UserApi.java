package com.gls.common.user.api.rpc;

import com.gls.common.user.api.model.UserModel;

/**
 * @author george
 */
public interface UserApi {
    /**
     * 修改用户密码
     *
     * @param userModel   用户信息
     * @param newPassword 新密码
     * @return 用户信息
     */
    UserModel updatePassword(UserModel userModel, String newPassword);

    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserModel loadUserByUsername(String username);
}
