package com.gls.job.executor.web.repository;

import com.gls.job.core.api.model.LogResultModel;

import java.util.Date;

/**
 * @author george
 */
public interface JobLogRepository {
    /**
     * getLogFileName
     *
     * @param logDateTime
     * @param logId
     * @return
     */
    String getLogFileName(Date logDateTime, Long logId);

    /**
     * logFileClean
     */
    void logFileClean();

    /**
     * readLog
     *
     * @param logFileName
     * @param fromLineNum
     * @return
     */
    LogResultModel readLog(String logFileName, Integer fromLineNum);

    /**
     * saveLog
     *
     * @param logFileName
     * @param appendLog
     */
    void saveLog(String logFileName, String appendLog);
}
