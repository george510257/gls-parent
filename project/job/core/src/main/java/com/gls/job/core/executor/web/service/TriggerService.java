package com.gls.job.core.executor.web.service;

import com.gls.job.core.api.model.TriggerModel;

/**
 * @author george
 */
public interface TriggerService {

    /**
     * idle Beat
     *
     * @param jobId
     * @return
     */
    boolean idleBeat(Long jobId);

    /**
     * run Trigger
     *
     * @param triggerModel
     * @throws Exception
     */
    void runTrigger(TriggerModel triggerModel) throws Exception;

    /**
     * remove Job Thread
     *
     * @param jobId
     * @return
     */
    String removeJobThread(Long jobId);

}
