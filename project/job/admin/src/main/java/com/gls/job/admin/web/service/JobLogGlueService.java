package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobLogGlue;

import java.util.Map;

/**
 * @author george
 */
public interface JobLogGlueService {
    /**
     * getIndex
     *
     * @param jobId
     * @return
     */
    Map<String, Object> getIndex(Long jobId);

    /**
     * save
     *
     * @param jobLogGlue
     */
    void save(JobLogGlue jobLogGlue);
}
