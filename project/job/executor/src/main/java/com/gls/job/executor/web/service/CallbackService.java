package com.gls.job.executor.web.service;

/**
 * @author george
 */
public interface CallbackService {
    /**
     * call back
     */
    void callback();

    /**
     * retry Call back
     */
    void retryCallback();
}
