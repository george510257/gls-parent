package com.gls.job.admin.web.service;

import com.gls.job.core.api.rpc.ExecutorApi;

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
}
