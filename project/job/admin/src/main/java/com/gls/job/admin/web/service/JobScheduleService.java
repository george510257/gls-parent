package com.gls.job.admin.web.service;

/**
 * @author george
 */
public interface JobScheduleService {
    /**
     * run
     *
     * @return
     */
    boolean run();

    /**
     * ring
     */
    void ring();
}
