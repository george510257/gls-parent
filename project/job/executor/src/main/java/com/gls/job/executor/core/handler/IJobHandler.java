package com.gls.job.executor.core.handler;

/**
 * job handler
 *
 * @author george
 */
public interface IJobHandler {

    /**
     * init handler, invoked when JobThread init
     *
     * @throws Exception
     */
    default void init() throws Exception {
    }

    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @throws Exception
     */
    void execute() throws Exception;

    /**
     * destroy handler, invoked when JobThread destroy
     *
     * @throws Exception
     */
    default void destroy() throws Exception {
    }

}
