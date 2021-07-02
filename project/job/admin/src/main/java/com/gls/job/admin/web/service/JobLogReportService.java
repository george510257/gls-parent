package com.gls.job.admin.web.service;

/**
 * @author george
 */
public interface JobLogReportService {
    /**
     * doJobLogReport
     *
     * @param lastCleanLogTime
     * @return
     */
    long doJobLogReport(long lastCleanLogTime);
}
