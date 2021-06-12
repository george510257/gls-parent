package com.gls.job.executor.thread;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.executor.web.service.CallbackService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class CallbackThread extends BaseThread {
    @Resource
    private CallbackService callbackService;

    @Override
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        callbackService.callback();
    }

    @Override
    protected void sleepExecute() throws Exception {

    }

    @Override
    protected void destroyExecute() throws Exception {
        callbackService.callback();
    }
}
