package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobGroup;

import java.util.List;

/**
 * @author george
 */
public interface JobGroupService {

    /**
     * get All Group List
     *
     * @return
     */
    List<JobGroup> getAllGroupList();
}
