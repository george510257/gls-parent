package com.gls.job.admin.web.service;

import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.model.JobLogGlue;
import com.gls.starter.data.jpa.base.BaseService;

import java.util.Map;

/**
 * @author george
 */
public interface JobLogGlueService extends BaseService<JobLogGlue,Object> {
    /**
     * getIndex
     *
     * @param jobId
     * @return
     */
    Map<String, Object> getIndex(Long jobId);
}
