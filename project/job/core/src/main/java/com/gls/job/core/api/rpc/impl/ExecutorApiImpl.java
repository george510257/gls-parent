package com.gls.job.core.api.rpc.impl;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.executor.handler.JobHandler;
import com.gls.job.core.executor.handler.impl.GlueJobHandler;
import com.gls.job.core.executor.handler.impl.ScriptJobHandler;
import com.xxl.job.core.executor.JobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.thread.JobThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xuxueli
 * @date 17/3/1
 */
@Slf4j
public class ExecutorApiImpl implements ExecutorApi {

    @Override
    public Result<String> beat() {
        return Result.SUCCESS;
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {

        // isRunningOrHasQueue
        boolean isRunningOrHasQueue = false;
        JobThread jobThread = JobExecutor.loadJobThread(idleBeatModel.getJobId());
        if (jobThread != null && jobThread.isRunningOrHasQueue()) {
            isRunningOrHasQueue = true;
        }

        if (isRunningOrHasQueue) {
            return new Result<>(Result.FAIL_CODE, "job thread is running or has trigger queue.");
        }
        return Result.SUCCESS;
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        // load old：jobHandler + jobThread
        JobThread jobThread = JobExecutor.loadJobThread(triggerModel.getJobId());
        JobHandler jobHandler = jobThread != null ? jobThread.getHandler() : null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        GlueType glueType = triggerModel.getGlueType();
        if (GlueType.BEAN == glueType) {

            // new jobHandler
            JobHandler newJobHandler = JobExecutor.loadJobHandler(triggerModel.getExecutorHandler());

            // valid old jobThread
            if (jobThread != null && jobHandler != newJobHandler) {
                // change handler, need kill old thread
                removeOldReason = "change jobHandler or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = newJobHandler;
                if (jobHandler == null) {
                    return new Result<>(Result.FAIL_CODE, "job handler [" + triggerModel.getExecutorHandler() + "] not found.");
                }
            }

        } else if (GlueType.GLUE_GROOVY == glueType) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof GlueJobHandler
                            && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdateTime() == triggerModel.getGlueUpdateTime())) {
                // change handler or glueSource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    JobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerModel.getGlueSource());
                    jobHandler = new GlueJobHandler(originJobHandler, triggerModel.getGlueUpdateTime());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return new Result<>(Result.FAIL_CODE, e.getMessage());
                }
            }
        } else if (glueType != null && glueType.isScript()) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof ScriptJobHandler
                            && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdateTime() == triggerModel.getGlueUpdateTime())) {
                // change script or glueSource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerModel.getJobId(), triggerModel.getGlueUpdateTime(), triggerModel.getGlueSource(), triggerModel.getGlueType());
            }
        } else {
            return new Result<>(Result.FAIL_CODE, "glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategy blockStrategy = triggerModel.getExecutorBlockStrategy();
            if (ExecutorBlockStrategy.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new Result<>(Result.FAIL_CODE, "block strategy effect：" + ExecutorBlockStrategy.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategy.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategy.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            }  // just queue trigger

        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = JobExecutor.registJobThread(triggerModel.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        return jobThread.pushTriggerQueue(triggerModel);
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        // kill handlerThread, and create new one
        JobThread jobThread = JobExecutor.loadJobThread(killModel.getJobId());
        if (jobThread != null) {
            JobExecutor.removeJobThread(killModel.getJobId(), "scheduling center kill job.");
            return Result.SUCCESS;
        }

        return new Result<>(Result.SUCCESS_CODE, "job thread already killed.");
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        // log filename: logPath/yyyy-MM-dd/9999.log
        String logFileName = XxlJobFileAppender.makeLogFileName(logModel.getLogDateTime(), logModel.getLogId());

        LogResultModel logResultModel = XxlJobFileAppender.readLog(logFileName, logModel.getFromLineNum());
        return new Result<>(logResultModel);
    }

}
