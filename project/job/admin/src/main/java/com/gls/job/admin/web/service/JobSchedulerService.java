package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.core.api.rpc.ExecutorApi;

import java.text.ParseException;
import java.util.Date;

/**
 * @author xuxueli 2018-10-28 00:18:17
 */

public interface JobSchedulerService {

    /**
     * get ExecutorApi
     *
     * @param executorAddress
     * @return
     */
    ExecutorApi getExecutorBiz(String executorAddress);

    /**
     * generateNextValidTime
     *
     * @param jobInfo
     * @param lastTime
     * @return
     * @throws ParseException
     */
    Date generateNextValidTime(JobInfo jobInfo, Date lastTime) throws ParseException;
}
