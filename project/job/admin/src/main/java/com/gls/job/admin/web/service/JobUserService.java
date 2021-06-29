package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;
import com.gls.starter.data.jpa.base.BaseService;

/**
 * @author georg2
 */
public interface JobUserService extends BaseService<JobUser, QueryJobUser> {
    /**
     * login
     *
     * @param username
     * @param password
     * @param ifRemember
     * @return
     */
    void login(String username, String password, boolean ifRemember);

    /**
     * login
     *
     * @return
     */
    void logout();

    /**
     * changePassword
     *
     * @param password
     */
    void changePassword(String password);
}
