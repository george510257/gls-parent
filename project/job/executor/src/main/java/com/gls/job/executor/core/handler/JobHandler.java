package com.gls.job.executor.core.handler;

/**
 * job handler
 *
 * @author xuxueli 2015-12-19 19:06:38
 */
public interface JobHandler {
    /**
     * destroy handler, invoked when JobThread destroy
     *
     * @throws Exception
     */
    default void destroy() throws Exception {
        // do something
    }

    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @throws Exception
     */
    void execute() throws Exception;

    /**
     * init handler, invoked when JobThread init
     *
     * @throws Exception
     */
    default void init() throws Exception {
        // do something
    }
}
