package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobUser;
import com.gls.job.core.api.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */

public interface JobUserService {

    String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";

    /**
     * login
     *
     * @param request
     * @param response
     * @param userName
     * @param password
     * @param ifRem
     * @return
     */
    Result<String> login(HttpServletRequest request, HttpServletResponse response, String userName, String password, boolean ifRem);

    /**
     * logout
     *
     * @param request
     * @param response
     * @return
     */
    Result<String> logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * ifLogin
     *
     * @param request
     * @param response
     * @return
     */
    JobUser ifLogin(HttpServletRequest request, HttpServletResponse response);
}
