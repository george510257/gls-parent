package com.gls.job.admin.web.service;

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
     * saveGlueSource
     *
     * @param jobId
     * @param glueSource
     * @param glueRemark
     */
    void saveGlueSource(Long jobId, String glueSource, String glueRemark);
}
