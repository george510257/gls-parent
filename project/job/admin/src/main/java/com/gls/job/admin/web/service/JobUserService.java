package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.starter.data.jpa.base.BaseService;

/**
 * @author george
 */
public interface JobUserService extends BaseService<JobUser, QueryJobUser> {
    /**
     * 修改密码
     *
     * @param password
     */
    void changePassword(String password);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param ifRemember
     * @return
     */
    void login(String username, String password, boolean ifRemember);

    /**
     * 登出
     *
     * @return
     */
    void logout();
}
