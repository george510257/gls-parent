package com.xxl.job.admin.core.thread;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobCompleter;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * job lose-monitor instance
 *
 * @author xuxueli 2015-9-1 18:05:56
 */
@Slf4j
public class JobCompleteHelper {

    private static JobCompleteHelper instance = new JobCompleteHelper();
    private ThreadPoolExecutor callbackThreadPool = null;

    // ---------------------- monitor ----------------------

    private Thread monitorThread;
    private volatile boolean toStop = false;
    @Resource
    private JobCompleter jobCompleter;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private I18nHelper i18nHelper;

    public static JobCompleteHelper getInstance() {
        return instance;
    }

    public void start() {

        // for callback
        callbackThreadPool = new ThreadPoolExecutor(
                2,
                20,
                30L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3000),
                r -> new Thread(r, "gls-job, admin JobLosedMonitorHelper-callbackThreadPool-" + r.hashCode()),
                (r, executor) -> {
                    r.run();
                    log.warn(">>>>>>>>>>> gls-job, callback too fast, match threadpool rejected handler(run now).");
                });

        // for monitor
        monitorThread = new Thread(() -> {

            // wait for JobTriggerPoolHelper-init
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }

            // monitor
            while (!toStop) {
                try {
                    // 任务结果丢失处理：调度记录停留在 "运行中" 状态超过10min，且对应执行器心跳注册失败不在线，则将本地调度主动标记失败；
                    Date losedTime = DateUtil.addMinutes(new Date(), -10);
                    List<Long> losedJobIds = jobLogDao.findLostJobIds(losedTime);

                    if (losedJobIds != null && losedJobIds.size() > 0) {
                        for (Long logId : losedJobIds) {

                            JobLog jobLog = new JobLog();
                            jobLog.setId(logId);

                            jobLog.setHandleTime(new Date());
                            jobLog.setHandleCode(Result.FAIL_CODE);
                            jobLog.setHandleMsg(i18nHelper.getString("job_log_lost_fail"));

                            jobCompleter.updateHandleInfoAndFinish(jobLog);
                        }

                    }
                } catch (Exception e) {
                    if (!toStop) {
                        log.error(">>>>>>>>>>> gls-job, job fail monitor thread error:{}", e);
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (Exception e) {
                    if (!toStop) {
                        log.error(e.getMessage(), e);
                    }
                }

            }

            log.info(">>>>>>>>>>> gls-job, JobLosedMonitorHelper stop");

        });
        monitorThread.setDaemon(true);
        monitorThread.setName("gls-job, admin JobLosedMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        toStop = true;

        // stop registryOrRemoveThreadPool
        callbackThreadPool.shutdownNow();

        // stop monitorThread (interrupt and wait)
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    // ---------------------- helper ----------------------

    public Result<String> callback(List<CallbackModel> callbackParamList) {

        callbackThreadPool.execute(() -> {
            for (CallbackModel callbackModel : callbackParamList) {
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
            return new Result<>(Result.FAIL_CODE, "log repeate callback.");
            // avoid repeat callback, trigger child job etc
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
        jobCompleter.updateHandleInfoAndFinish(log);

        return Result.SUCCESS;
    }

}
