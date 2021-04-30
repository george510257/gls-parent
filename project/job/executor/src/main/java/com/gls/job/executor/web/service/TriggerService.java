package com.gls.job.executor.web.service;

import com.gls.job.core.api.model.TriggerModel;

/**
 * @author george
 */
public interface TriggerService {

    /**
     * idleBeat
     *
     * @param jobId
     * @return
     */
    boolean idleBeat(Long jobId);

    /**
     * run
     *
     * @param triggerModel
     * @return
     */
    boolean run(TriggerModel triggerModel) throws Exception;

    /**
     * kill
     *
     * @param jobId
     * @return
     */
    boolean kill(Long jobId);

}
