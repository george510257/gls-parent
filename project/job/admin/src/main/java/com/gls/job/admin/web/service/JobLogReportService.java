package com.gls.job.admin.web.service;

/**
 * @author george
 */
public interface JobLogReportService {

    /**
     * do JobLogReport
     *
     * @param lastCleanLogTime
     * @return
     */
    long doJobLogReport(long lastCleanLogTime);
}
