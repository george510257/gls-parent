package com.gls.job.executor.web.service;

import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;

/**
 * @author george
 */
public interface LogFileService {

    /**
     * clean Log File
     */
    void cleanLogFile();

    /**
     * read Log File
     *
     * @param logModel
     * @return
     */
    LogResultModel readLogFile(LogModel logModel);
}
