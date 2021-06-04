package com.xxl.job.core.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * handler thread
 *
 * @author xuxueli 2016-1-16 19:52:47
 */
public class JobThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(JobThread.class);

    private int jobId;
    private IJobHandler handler;
    private LinkedBlockingQueue<TriggerModel> triggerQueue;
    private Set<Long> triggerLogIdSet;        // avoid repeat trigger for the same TRIGGER_LOG_ID

    private volatile boolean toStop = false;
    private String stopReason;

    private boolean running = false;    // if running job
    private int idleTimes = 0;            // idel times

    public JobThread(int jobId, IJobHandler handler) {
        this.jobId = jobId;
        this.handler = handler;
        this.triggerQueue = new LinkedBlockingQueue<TriggerModel>();
        this.triggerLogIdSet = Collections.synchronizedSet(new HashSet<Long>());
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
        // avoid repeat
        if (triggerLogIdSet.contains(triggerModel.getLogId())) {
            logger.info(">>>>>>>>>>> repeate trigger job, logId:{}", triggerModel.getLogId());
            return new Result<String>(Result.FAIL_CODE, "repeate trigger job, logId:" + triggerModel.getLogId());
        }

        triggerLogIdSet.add(triggerModel.getLogId());
        triggerQueue.add(triggerModel);
        return Result.SUCCESS;
    }

    /**
     * kill job thread
     *
     * @param stopReason
     */
    public void toStop(String stopReason) {
        /**
         * Thread.interrupt只支持终止线程的阻塞状态(wait、join、sleep)，
         * 在阻塞出抛出InterruptedException异常,但是并不会终止运行的线程本身；
         * 所以需要注意，此处彻底销毁本线程，需要通过共享变量方式；
         */
        this.toStop = true;
        this.stopReason = stopReason;
    }

    /**
     * is running job
     *
     * @return
     */
    public boolean isRunningOrHasQueue() {
        return running || triggerQueue.size() > 0;
    }

    @Override
    public void run() {

        // init
        try {
            handler.init();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }

        // execute
        while (!toStop) {
            running = false;
            idleTimes++;

            TriggerModel triggerModel = null;
            try {
                // to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
                triggerModel = triggerQueue.poll(3L, TimeUnit.SECONDS);
                if (triggerModel != null) {
                    running = true;
                    idleTimes = 0;
                    triggerLogIdSet.remove(triggerModel.getLogId());

                    // log filename, like "logPath/yyyy-MM-dd/9999.log"
                    String logFileName = XxlJobFileAppender.makeLogFileName(new Date(triggerModel.getLogDateTime()), triggerModel.getLogId());
                    XxlJobContext xxlJobContext = new XxlJobContext(
                            triggerModel.getJobId(),
                            triggerModel.getExecutorParams(),
                            logFileName,
                            triggerModel.getBroadcastIndex(),
                            triggerModel.getBroadcastTotal());

                    // init job context
                    XxlJobContext.setXxlJobContext(xxlJobContext);

                    // execute
                    XxlJobHelper.log("<br>----------- xxl-job job execute start -----------<br>----------- Param:" + xxlJobContext.getJobParam());

                    if (triggerModel.getExecutorTimeout() > 0) {
                        // limit timeout
                        Thread futureThread = null;
                        try {
                            FutureTask<Boolean> futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
                                @Override
                                public Boolean call() throws Exception {

                                    // init job context
                                    XxlJobContext.setXxlJobContext(xxlJobContext);

                                    handler.execute();
                                    return true;
                                }
                            });
                            futureThread = new Thread(futureTask);
                            futureThread.start();

                            Boolean tempResult = futureTask.get(triggerModel.getExecutorTimeout(), TimeUnit.SECONDS);
                        } catch (TimeoutException e) {

                            XxlJobHelper.log("<br>----------- xxl-job job execute timeout");
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
                    if (XxlJobContext.getXxlJobContext().getHandleCode() <= 0) {
                        XxlJobHelper.handleFail("job handle result lost.");
                    } else {
                        String tempHandleMsg = XxlJobContext.getXxlJobContext().getHandleMsg();
                        tempHandleMsg = (tempHandleMsg != null && tempHandleMsg.length() > 50000)
                                ? tempHandleMsg.substring(0, 50000).concat("...")
                                : tempHandleMsg;
                        XxlJobContext.getXxlJobContext().setHandleMsg(tempHandleMsg);
                    }
                    XxlJobHelper.log("<br>----------- xxl-job job execute end(finish) -----------<br>----------- Result: handleCode="
                            + XxlJobContext.getXxlJobContext().getHandleCode()
                            + ", handleMsg = "
                            + XxlJobContext.getXxlJobContext().getHandleMsg()
                    );

                } else {
                    if (idleTimes > 30) {
                        if (triggerQueue.size() == 0) {    // avoid concurrent trigger causes jobId-lost
                            XxlJobExecutor.removeJobThread(jobId, "excutor idel times over limit.");
                        }
                    }
                }
            } catch (Throwable e) {
                if (toStop) {
                    XxlJobHelper.log("<br>----------- JobThread toStop, stopReason:" + stopReason);
                }

                // handle result
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                String errorMsg = stringWriter.toString();

                XxlJobHelper.handleFail(errorMsg);

                XxlJobHelper.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- xxl-job job execute end(error) -----------");
            } finally {
                if (triggerModel != null) {
                    // callback handler info
                    if (!toStop) {
                        // commonm
                        TriggerCallbackThread.pushCallBack(new CallbackModel(
                                triggerModel.getLogId(),
                                triggerModel.getLogDateTime(),
                                XxlJobContext.getXxlJobContext().getHandleCode(),
                                XxlJobContext.getXxlJobContext().getHandleMsg())
                        );
                    } else {
                        // is killed
                        TriggerCallbackThread.pushCallBack(new CallbackModel(
                                triggerModel.getLogId(),
                                triggerModel.getLogDateTime(),
                                XxlJobContext.HANDLE_COCE_FAIL,
                                stopReason + " [job running, killed]")
                        );
                    }
                }
            }
        }

        // callback trigger request in queue
        while (triggerQueue != null && triggerQueue.size() > 0) {
            TriggerModel triggerModel = triggerQueue.poll();
            if (triggerModel != null) {
                // is killed
                TriggerCallbackThread.pushCallBack(new CallbackModel(
                        triggerModel.getLogId(),
                        triggerModel.getLogDateTime(),
                        XxlJobContext.HANDLE_COCE_FAIL,
                        stopReason + " [job not executed, in the job queue, killed.]")
                );
            }
        }

        // destroy
        try {
            handler.destroy();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }

        logger.info(">>>>>>>>>>> xxl-job JobThread stoped, hashCode:{}", Thread.currentThread());
    }
}
