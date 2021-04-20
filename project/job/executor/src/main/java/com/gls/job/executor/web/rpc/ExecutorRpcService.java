package com.gls.job.executor.web.rpc;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.enums.ExecutorBlockStrategyEnum;
import com.gls.job.core.enums.GlueTypeEnum;
import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.glue.GlueFactory;
import com.gls.job.executor.handler.IJobHandler;
import com.gls.job.executor.handler.impl.GlueJobHandler;
import com.gls.job.executor.handler.impl.ScriptJobHandler;
import com.gls.job.executor.helper.XxlJobFileHelper;
import com.gls.job.executor.thread.JobThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author george
 */
@Slf4j
public class ExecutorRpcService implements ExecutorApi {

    @Override
    public Result<String> beat() {
        return Result.SUCCESS;
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {

        // isRunningOrHasQueue
        boolean isRunningOrHasQueue = false;
        JobThread jobThread = XxlJobExecutor.loadJobThread(idleBeatModel.getJobId());
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
        JobThread jobThread = XxlJobExecutor.loadJobThread(triggerModel.getJobId());
        IJobHandler jobHandler = jobThread != null ? jobThread.getHandler() : null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        GlueTypeEnum glueTypeEnum = GlueTypeEnum.match(triggerModel.getGlueType());
        if (GlueTypeEnum.BEAN == glueTypeEnum) {

            // new jobHandler
            IJobHandler newJobHandler = XxlJobExecutor.loadJobHandler(triggerModel.getExecutorHandler());

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

        } else if (GlueTypeEnum.GLUE_GROOVY == glueTypeEnum) {

            // valid old jobThread
            if (jobThread != null
                    && !(jobThread.getHandler() instanceof GlueJobHandler
                    && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdateTime() == triggerModel.getGlueUpdateTime())) {
                // change handler or glueSource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    IJobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerModel.getGlueSource());
                    jobHandler = new GlueJobHandler(originJobHandler, triggerModel.getGlueUpdateTime());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return new Result<>(Result.FAIL_CODE, e.getMessage());
                }
            }
        } else if (glueTypeEnum != null && glueTypeEnum.isScript()) {

            // valid old jobThread

            if (jobThread != null
                    && !(jobThread.getHandler() instanceof ScriptJobHandler
                    && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdateTime() == triggerModel.getGlueUpdateTime())) {
                // change script or glueSource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerModel.getJobId(), triggerModel.getGlueUpdateTime(), triggerModel.getGlueSource(), GlueTypeEnum.match(triggerModel.getGlueType()));
            }
        } else {
            return new Result<>(Result.FAIL_CODE, "glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerModel.getExecutorBlockStrategy(), null);
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new Result<>(Result.FAIL_CODE, "block strategy effect：" + ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            }  // just queue trigger

        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = XxlJobExecutor.registJobThread(triggerModel.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        return jobThread.pushTriggerQueue(triggerModel);
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        // kill handlerThread, and create new one
        JobThread jobThread = XxlJobExecutor.loadJobThread(killModel.getJobId());
        if (jobThread != null) {
            XxlJobExecutor.removeJobThread(killModel.getJobId(), "scheduling center kill job.");
            return Result.SUCCESS;
        }

        return new Result<>(Result.SUCCESS_CODE, "job thread already killed.");
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        // log filename: logPath/yyyy-MM-dd/9999.log
        String logFileName = XxlJobFileHelper.makeLogFileName(new Date(logModel.getLogDateTime()), logModel.getLogId());

        LogResultModel logResult = XxlJobFileHelper.readLog(logFileName, logModel.getFromLineNumber());
        return new Result<>(logResult);
    }

}
