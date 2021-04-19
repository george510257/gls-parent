package com.gls.job.executor.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.executor.holder.CallbackQueueHolder;
import com.gls.job.executor.web.service.CallbackService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Slf4j
public class CallbackThread extends Thread {

    private final CallbackService callbackService;
    @Setter
    private volatile boolean toStop = false;

    public CallbackThread(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @Override
    public void run() {
        // normal callback
        while (!toStop) {
            try {
                CallbackModel callback = CallbackQueueHolder.getInstance().take();

                // callback list param
                List<CallbackModel> callbackModelList = new ArrayList<>();
                int drainToNum = CallbackQueueHolder.getInstance().drainTo(callbackModelList);
                callbackModelList.add(callback);

                // callback, will retry if error
                if (callbackModelList.size() > 0) {
                    doCallback(callbackModelList);
                }
            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        // last callback
        try {
            List<CallbackModel> callbackModelList = new ArrayList<>();
            int drainToNum = CallbackQueueHolder.getInstance().drainTo(callbackModelList);
            if (callbackModelList.size() > 0) {
                doCallback(callbackModelList);
            }
        } catch (Exception e) {
            if (!toStop) {
                log.error(e.getMessage(), e);
            }
        }
        log.info(">>>>>>>>>>> gls-job, executor callback thread destroy.");
    }

    private void doCallback(List<CallbackModel> callbackModelList) {
        callbackService.doCallback(callbackModelList);
    }

}
