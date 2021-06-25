package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobUser;

import java.util.Map;

/**
 * @author georg2
 */
public interface JobUserService {

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
     * getPageList
     *
     * @param username
     * @param role
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getPageList(String username, int role, int start, int length);

    /**
     * addUser
     *
     * @param jobUser
     * @return
     */
    void addUser(JobUser jobUser);

    /**
     * updateUser
     *
     * @param jobUser
     */
    void updateUser(JobUser jobUser);

    /**
     * removeUser
     *
     * @param id
     */
    void removeUser(Long id);

    /**
     * changePassword
     *
     * @param password
     */
    void changePassword(String password);
}
