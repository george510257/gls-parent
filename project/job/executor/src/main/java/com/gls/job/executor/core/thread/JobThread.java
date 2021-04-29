package com.gls.job.executor.core.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.base.thread.BaseThread;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.executor.core.handler.IJobHandler;
import com.gls.job.executor.core.helper.XxlJobFileHelper;
import com.gls.job.executor.core.helper.XxlJobHelper;
import com.gls.job.executor.core.holder.CallbackQueueHolder;
import com.gls.job.executor.core.holder.JobContextHolder;
import com.gls.job.executor.core.holder.JobThreadHolder;
import com.gls.job.executor.core.holder.TriggerQueueHolder;
import com.gls.job.executor.web.model.JobContextModel;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author george
 */
@Slf4j
public class JobThread extends BaseThread {

    private final int jobId;
    private final IJobHandler handler;
    private final TriggerQueueHolder triggerQueueHolder = new TriggerQueueHolder();

    // if running job

    private boolean running = false;

    // idel times

    private int idleTimes = 0;

    public JobThread(int jobId, IJobHandler handler) {
        this.jobId = jobId;
        this.handler = handler;
    }

    public IJobHandler getHandler() {
        return handler;
    }

    /**
     * new trigger to queue
     *
     * @param triggerModel
     * @return
     */
    public Result<String> pushTriggerQueue(TriggerModel triggerModel) {
        triggerQueueHolder.push(triggerModel);
        return Result.SUCCESS;
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
        handler.init();
    }

    @Override
    protected void doExecute() {
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
                String logFileName = XxlJobFileHelper.makeLogFileName(new Date(triggerModel.getLogDateTime()), triggerModel.getLogId());
                JobContextModel glsJobContext = new JobContextModel(triggerModel.getJobId(), triggerModel.getExecutorParams(), logFileName, triggerModel.getBroadcastIndex(), triggerModel.getBroadcastTotal());

                // init job context
                JobContextHolder.getInstance().set(glsJobContext);

                // execute
                XxlJobHelper.log("<br>----------- gls-job job execute start -----------<br>----------- Param:" + glsJobContext.getJobParam());

                if (triggerModel.getExecutorTimeout() > 0) {
                    // limit timeout
                    Thread futureThread = null;
                    try {
                        FutureTask<Boolean> futureTask = new FutureTask<>(() -> {

                            // init job context
                            JobContextHolder.getInstance().set(glsJobContext);

                            handler.execute();
                            return true;
                        });
                        futureThread = new Thread(futureTask);
                        futureThread.start();

                        Boolean tempResult = futureTask.get(triggerModel.getExecutorTimeout(), TimeUnit.SECONDS);
                    } catch (TimeoutException e) {

                        XxlJobHelper.log("<br>----------- gls-job job execute timeout");
                        XxlJobHelper.log(e);

                        // handle result
                        XxlJobHelper.handleTimeout("job execute timeout ");
                    } finally {
                        futureThread.interrupt();
                    }
                } else {
                    // just execute
                    handler.execute();
                }

                // valid execute handle data
                if (JobContextHolder.getInstance().get().getHandleCode() <= 0) {
                    XxlJobHelper.handleFail("job handle result lost.");
                } else {
                    String tempHandleMsg = JobContextHolder.getInstance().get().getHandleMsg();
                    tempHandleMsg = (tempHandleMsg != null && tempHandleMsg.length() > 50000)
                            ? tempHandleMsg.substring(0, 50000).concat("...")
                            : tempHandleMsg;
                    JobContextHolder.getInstance().get().setHandleMsg(tempHandleMsg);
                }
                XxlJobHelper.log("<br>----------- gls-job job execute end(finish) -----------<br>----------- Result: handleCode="
                        + JobContextHolder.getInstance().get().getHandleCode()
                        + ", handleMsg = "
                        + JobContextHolder.getInstance().get().getHandleMsg()
                );

            } else {
                if (idleTimes > 30) {
                    if (triggerQueueHolder.isEmpty()) {
                        // avoid concurrent trigger causes jobId-lost
                        JobThreadHolder.getInstance().remove(jobId, "excutor idel times over limit.");
                    }
                }
            }
        } catch (Throwable e) {
            if (this.isStop()) {
                XxlJobHelper.log("<br>----------- JobThread toStop, stopReason:" + this.getStopReason());
            }

            // handle result
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            String errorMsg = stringWriter.toString();

            XxlJobHelper.handleFail(errorMsg);

            XxlJobHelper.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- gls-job job execute end(error) -----------");
        } finally {
            if (triggerModel != null) {
                // callback handler info
                if (!this.isStop()) {
                    // commonm
                    CallbackQueueHolder.getInstance().push(new CallbackModel(triggerModel.getLogId(), triggerModel.getLogDateTime(), JobContextHolder.getInstance().get().getHandleCode(),
                            JobContextHolder.getInstance().get().getHandleMsg())
                    );
                } else {
                    // is killed
                    CallbackQueueHolder.getInstance().push(new CallbackModel(triggerModel.getLogId(), triggerModel.getLogDateTime(), JobConstants.HANDLE_CODE_FAIL, this.getStopReason() + " [job running, killed]"));
                }
            }
        }
    }

    @Override
    protected void sleepExecute() {

    }

    @Override
    protected void destroyExecute() {
        // callback trigger request in queue
        while (!triggerQueueHolder.isEmpty()) {
            TriggerModel triggerModel = triggerQueueHolder.pop();
            if (triggerModel != null) {
                // is killed
                CallbackQueueHolder.getInstance().push(new CallbackModel(triggerModel.getLogId(), triggerModel.getLogDateTime(), JobConstants.HANDLE_CODE_FAIL, this.getStopReason() + " [job not executed, in the job queue, killed.]"));
            }
        }

        // destroy
        try {
            handler.destroy();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }

        log.info(">>>>>>>>>>> gls-job JobThread stoped, hashCode:{}", Thread.currentThread());
    }
}