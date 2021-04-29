package com.gls.job.executor.core.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.base.thread.BaseThread;
import com.gls.job.executor.core.holder.CallbackQueueHolder;
import com.gls.job.executor.web.service.CallbackService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Component
public class CallbackThread extends BaseThread {

    @Resource
    private CallbackService callbackService;

    @Override
    protected void initExecute() {
    }

    @Override
    protected void doExecute() {
        List<CallbackModel> callbackModelList = CallbackQueueHolder.getInstance().pops();
        // callback, will retry if error
        if (callbackModelList.size() > 0) {
            doCallback(callbackModelList);
        }
    }

    @Override
    protected void sleepExecute() {
    }

    @Override
    protected void destroyExecute() {
        List<CallbackModel> callbackModelList = CallbackQueueHolder.getInstance().pops();
        if (callbackModelList.size() > 0) {
            doCallback(callbackModelList);
        }
    }

    private void doCallback(List<CallbackModel> callbackModelList) {
        callbackService.doCallback(callbackModelList);
    }

}
