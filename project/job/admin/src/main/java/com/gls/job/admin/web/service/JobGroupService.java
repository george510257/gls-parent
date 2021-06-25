package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobGroup;

import java.util.List;
import java.util.Map;

/**
 * @author george
 */
public interface JobGroupService {

    /**
     * getAllList
     *
     * @return
     */
    List<JobGroup> getAllList();

    /**
     * get PageList
     *
     * @param appname
     * @param title
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getPageList(String appname, String title, int start, int length);

    /**
     * addJobGroup
     *
     * @param jobGroup
     */
    void addJobGroup(JobGroup jobGroup);

    /**
     * updateJobGroup
     *
     * @param jobGroup
     */
    void updateJobGroup(JobGroup jobGroup);

    /**
     * removeJobGroup
     *
     * @param id
     */
    void removeJobGroup(Long id);

    /**
     * loadJobGroupById
     *
     * @param id
     * @return
     */
    JobGroup loadJobGroupById(Long id);
}
