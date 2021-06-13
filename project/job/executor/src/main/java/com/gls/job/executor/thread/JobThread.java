package com.gls.job.executor.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.common.constants.JobConstants;
import com.gls.job.executor.context.JobContext;
import com.gls.job.executor.context.JobContextHolder;
import com.gls.job.executor.handler.JobHandler;
import com.gls.job.executor.queue.CallbackQueueHolder;
import com.gls.job.executor.queue.TriggerQueueHolder;
import com.gls.job.executor.web.service.JobLogService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * handler thread
 *
 * @author xuxueli 2016-1-16 19:52:47
 */
@Slf4j
public class JobThread extends BaseThread {

    @Getter
    private final Long jobId;
    @Getter
    private final JobHandler jobHandler;

    private final JobLogService jobLogService;
    private final JobThreadHolder jobThreadHolder;

    @Getter
    private final TriggerQueueHolder triggerQueueHolder = new TriggerQueueHolder();

    private final JobContextHolder jobContextHolder = JobContextHolder.getInstance();

    private final CallbackQueueHolder callbackQueueHolder;

    /**
     * if running job
     */
    private boolean running = false;
    /**
     * idel times
     */
    private int idleTimes = 0;

    public JobThread(Long jobId, JobHandler jobHandler, JobLogService jobLogService, JobThreadHolder jobThreadHolder, CallbackQueueHolder callbackQueueHolder) {
        this.jobId = jobId;
        this.jobHandler = jobHandler;
        this.jobLogService = jobLogService;
        this.jobThreadHolder = jobThreadHolder;
        this.callbackQueueHolder = callbackQueueHolder;
    }

    /**
     * is running job
     *
     * @return
     */
    public boolean isRunningOrHasQueue() {
        return running || !triggerQueueHolder.isEmpty();
    }

    @Override
    protected void initExecute() throws Exception {
        jobHandler.init();
    }

    @Override
    protected void doExecute() throws Exception {
        running = false;
        idleTimes++;

        TriggerModel triggerModel = null;
        try {
            // to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
            triggerModel = triggerQueueHolder.pop();
            if (triggerModel != null) {
                running = true;
                idleTimes = 0;

                // log filename, like "logPath/yyyy-MM-dd/9999.log"
                String logFileName = jobLogService.getLogFileName(triggerModel.getLogDateTime(), triggerModel.getLogId());
                JobContext jobContext = new JobContext(triggerModel.getJobId(), triggerModel.getExecutorParams(), logFileName, triggerModel.getBroadcastIndex(), triggerModel.getBroadcastTotal());

                // init job context
                jobContextHolder.set(jobContext);

                // execute
                jobLogService.log("<br>----------- xxl-job job execute start -----------<br>----------- Param:" + jobContext.getJobParam());

                if (triggerModel.getExecutorTimeout() > 0) {
                    // limit timeout
                    Thread futureThread = null;
                    try {
                        FutureTask<Boolean> futureTask = new FutureTask<>(() -> {

                            // init job context
                            jobContextHolder.set(jobContext);

                            jobHandler.execute();
                            return true;
                        });
                        futureThread = new Thread(futureTask);
                        futureThread.start();

                        Boolean tempResult = futureTask.get(triggerModel.getExecutorTimeout(), TimeUnit.SECONDS);
                    } catch (TimeoutException e) {

                        jobLogService.log("<br>----------- xxl-job job execute timeout");
                        jobLogService.log(e);

                        // handle result
                        jobContextHolder.handleTimeout("job execute timeout ");
                    } finally {
                        futureThread.interrupt();
                    }
                } else {
                    // just execute
                    jobHandler.execute();
                }

                // valid execute handle data
                if (jobContextHolder.get().getHandleCode() <= 0) {
                    jobContextHolder.handleFail("job handle result lost.");
                } else {
                    String tempHandleMsg = jobContextHolder.get().getHandleMsg();
                    tempHandleMsg = (tempHandleMsg != null && tempHandleMsg.length() > 50000)
                            ? tempHandleMsg.substring(0, 50000).concat("...")
                            : tempHandleMsg;
                    jobContextHolder.get().setHandleMsg(tempHandleMsg);
                }
                jobLogService.log("<br>----------- xxl-job job execute end(finish) -----------<br>----------- Result: handleCode="
                        + jobContextHolder.get().getHandleCode()
                        + ", handleMsg = "
                        + jobContextHolder.get().getHandleMsg()
                );

            } else {
                if (idleTimes > 30) {
                    if (triggerQueueHolder.isEmpty()) {
                        // avoid concurrent trigger causes jobId-lost
                        jobThreadHolder.remove(jobId, "excutor idel times over limit.");
                    }
                }
            }
        } catch (Throwable e) {
            if (this.isStop()) {
                jobLogService.log("<br>----------- JobThread toStop, stopReason:" + this.getStopReason());
            }

            // handle result
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            String errorMsg = stringWriter.toString();

            jobContextHolder.handleFail(errorMsg);

            jobLogService.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- xxl-job job execute end(error) -----------");
        } finally {
            if (triggerModel != null) {
                // callback handler info
                if (!this.isStop()) {
                    // commonm
                    callbackQueueHolder.push(new CallbackModel(
                            triggerModel.getLogId(),
                            triggerModel.getLogDateTime(),
                            jobContextHolder.get().getHandleCode(),
                            jobContextHolder.get().getHandleMsg())
                    );
                } else {
                    // is killed
                    callbackQueueHolder.push(new CallbackModel(
                            triggerModel.getLogId(),
                            triggerModel.getLogDateTime(),
                            JobConstants.HANDLE_CODE_FAIL,
                            this.getStopReason() + " [job running, killed]")
                    );
                }
            }
        }
    }

    @Override
    protected void sleepExecute() throws Exception {

    }

    @Override
    protected void destroyExecute() throws Exception {
        // callback trigger request in queue
        while (!triggerQueueHolder.isEmpty()) {
            TriggerModel triggerModel = triggerQueueHolder.pop();
            if (triggerModel != null) {
                // is killed
                callbackQueueHolder.push(new CallbackModel(
                        triggerModel.getLogId(),
                        triggerModel.getLogDateTime(),
                        JobConstants.HANDLE_CODE_FAIL,
                        this.getStopReason() + " [job not executed, in the job queue, killed.]"));
            }
        }

        // destroy
        try {
            jobHandler.destroy();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }

        log.info(">>>>>>>>>>> gls-job JobThread stoped, hashCode:{}", Thread.currentThread());
    }
}
