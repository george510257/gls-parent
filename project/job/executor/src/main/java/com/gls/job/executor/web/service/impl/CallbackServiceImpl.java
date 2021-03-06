package com.gls.job.executor.web.service.impl;

import com.gls.framework.api.result.Result;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.api.rpc.holder.AdminApiHolder;
import com.gls.job.executor.core.context.JobContext;
import com.gls.job.executor.core.context.JobContextHolder;
import com.gls.job.executor.core.queue.CallbackQueueHolder;
import com.gls.job.executor.web.repository.CallbackRepository;
import com.gls.job.executor.web.service.CallbackService;
import com.gls.job.executor.web.service.JobLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Service
public class CallbackServiceImpl implements CallbackService {
    @Resource
    private JobContextHolder jobContextHolder;
    @Resource
    private CallbackQueueHolder callbackQueueHolder;
    @Resource
    private CallbackRepository callbackRepository;
    @Resource
    private JobLogService jobLogService;
    @Resource
    private AdminApiHolder adminApiHolder;

    @Override
    public void callback() {
        List<CallbackModel> callbackModels = callbackQueueHolder.pops();
        if (!ObjectUtils.isEmpty(callbackModels)) {
            doCallback(callbackModels);
        }
    }

    @Override
    public void retryCallback() {
        List<CallbackModel> callbackModels = callbackRepository.getAll();
        if (!ObjectUtils.isEmpty(callbackModels)) {
            doCallback(callbackModels);
        }
    }

    private void callbackLog(List<CallbackModel> callbackModels, String logContent) {
        for (CallbackModel callbackModel : callbackModels) {
            String logFileName = jobLogService.getLogFileName(callbackModel.getLogDateTime(), callbackModel.getLogId());
            jobContextHolder.set(new JobContext(
                    -1L,
                    null,
                    logFileName,
                    -1,
                    -1));
            jobLogService.log(logContent);
        }
    }

    private void doCallback(List<CallbackModel> callbackModels) {
        boolean callbackRet = false;
        // callback, will retry if error
        for (AdminApi adminApi : adminApiHolder.loadAll()) {
            try {
                Result<String> callbackResult = adminApi.callback(callbackModels);
                if (callbackResult != null && Result.SUCCESS_CODE.equals(callbackResult.getCode())) {
                    callbackLog(callbackModels, "<br>----------- gls-job job callback finish.");
                    callbackRet = true;
                    break;
                } else {
                    callbackLog(callbackModels, "<br>----------- gls-job job callback fail, callbackResult:" + callbackResult);
                }
            } catch (Exception e) {
                callbackLog(callbackModels, "<br>----------- gls-job job callback error, errorMsg:" + e.getMessage());
            }
        }
        if (!callbackRet) {
            callbackRepository.save(callbackModels);
        }
    }
}
