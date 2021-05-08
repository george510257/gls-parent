package com.gls.job.admin.core.server;

import com.gls.job.admin.core.thread.JobCompleteMonitorThread;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.entity.JobLog;
import com.gls.job.admin.web.service.JobCompleterService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * job lose-monitor instance
 *
 * @author george 2015-9-1 18:05:56
 */
@Slf4j
@Component
public class JobCompleteServer {

    private static final JobCompleteServer INSTANCE = new JobCompleteServer();
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobCompleterService jobCompleterService;

    private ThreadPoolExecutor callbackThreadPool = null;

    // ---------------------- monitor ----------------------

    private JobCompleteMonitorThread monitorThread;

    public static JobCompleteServer getInstance() {
        return INSTANCE;
    }

    public void start() {

        // for callback
        callbackThreadPool = new ThreadPoolExecutor(2, 20, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3000),
                r -> new Thread(r, "gls-job, admin JobLosedMonitorHelper-callbackThreadPool-" + r.hashCode()),
                (r, executor) -> {
                    r.run();
                    log.warn(">>>>>>>>>>> gls-job, callback too fast, match threadpool rejected handler(run now).");
                });

        // for monitor
        monitorThread = new JobCompleteMonitorThread();
        monitorThread.setDaemon(true);
        monitorThread.setName("gls-job, admin JobLosedMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        monitorThread.toStop("");
        // stop registryOrRemoveThreadPool
        callbackThreadPool.shutdownNow();
    }

    // ---------------------- helper ----------------------

    public Result<String> callback(List<CallbackModel> callbackModelList) {

        callbackThreadPool.execute(() -> {
            for (CallbackModel callbackModel : callbackModelList) {
                Result<String> callbackResult = callback(callbackModel);
                log.debug(">>>>>>>>> JobApiController.callback {}, callbackModel={}, callbackResult={}",
                        (callbackResult.getCode() == Result.SUCCESS_CODE ? "success" : "fail"), callbackModel, callbackResult);
            }
        });

        return Result.SUCCESS;
    }

    private Result<String> callback(CallbackModel callbackModel) {
        // valid log item
        JobLog log = jobLogDao.load(callbackModel.getLogId());
        if (log == null) {
            return new Result<>(Result.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            // avoid repeat callback, trigger child job etc
            return new Result<>(Result.FAIL_CODE, "log repeate callback.");
        }

        // handle msg
        StringBuilder handleMsg = new StringBuilder();
        if (log.getHandleMsg() != null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (callbackModel.getHandleMsg() != null) {
            handleMsg.append(callbackModel.getHandleMsg());
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(callbackModel.getHandleCode());
        log.setHandleMsg(handleMsg.toString());
        jobCompleterService.updateHandleInfoAndFinish(log);

        return Result.SUCCESS;
    }

}
