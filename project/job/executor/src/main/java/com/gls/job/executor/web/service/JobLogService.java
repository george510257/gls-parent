package com.gls.job.executor.web.service;

import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;

import java.util.Date;

/**
 * @author george
 */
public interface JobLogService {

    /**
     * logFileClean
     *
     * @param logRetentionDays
     */
    void logFileClean(int logRetentionDays);

    /**
     * read Log
     *
     * @param logModel
     * @return
     */
    LogResultModel readLog(LogModel logModel);

    /**
     * get Log File Name
     *
     * @param logDateTime
     * @param logId
     * @return
     */
    String getLogFileName(Date logDateTime, Long logId);

    /**
     * append log with pattern
     *
     * @param appendLogPattern
     * @param appendLogArguments
     * @return
     */
    boolean log(String appendLogPattern, Object... appendLogArguments);

    /**
     * append exception stack
     *
     * @param e
     * @return
     */
    boolean log(Throwable e);
}
