package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobUser;
import com.gls.job.admin.web.model.query.QueryJobUser;

/**
 * @author georg2
 */
public interface JobUserService extends JobService<JobUser, QueryJobUser> {
    /**
     * login
     *
     * @param userName
     * @param password
     * @param ifRemember
     * @return
     */
    String login(String userName, String password, boolean ifRemember);

    /**
     * login
     *
     * @return
     */
    String logout();

    /**
     * changePassword
     *
     * @param password
     */
    void changePassword(String password);
}
