package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.query.QueryJobGroup;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.List;

/**
 * @author george
 */
public interface JobGroupService extends BaseService<JobGroup, QueryJobGroup> {
    /**
     * getByLoginUser
     *
     * @return
     */
    List<JobGroup> getByLoginUser();
}
