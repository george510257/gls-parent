package com.gls.job.core.api.rpc.impl;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.impl.GlueJobHandler;
import com.xxl.job.core.handler.impl.ScriptJobHandler;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.thread.JobThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by xuxueli on 17/3/1.
 */
public class ExecutorApiImpl implements ExecutorApi {
    private static Logger logger = LoggerFactory.getLogger(ExecutorApiImpl.class);

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
            return new Result<String>(Result.FAIL_CODE, "job thread is running or has trigger queue.");
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

            // new jobhandler
            IJobHandler newJobHandler = XxlJobExecutor.loadJobHandler(triggerModel.getExecutorHandler());

            // valid old jobThread
            if (jobThread != null && jobHandler != newJobHandler) {
                // change handler, need kill old thread
                removeOldReason = "change jobhandler or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = newJobHandler;
                if (jobHandler == null) {
                    return new Result<String>(Result.FAIL_CODE, "job handler [" + triggerModel.getExecutorHandler() + "] not found.");
                }
            }

        } else if (GlueTypeEnum.GLUE_GROOVY == glueTypeEnum) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof GlueJobHandler
                            && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdatetime() == triggerModel.getGlueUpdatetime())) {
                // change handler or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    IJobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerModel.getGlueSource());
                    jobHandler = new GlueJobHandler(originJobHandler, triggerModel.getGlueUpdatetime());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    return new Result<String>(Result.FAIL_CODE, e.getMessage());
                }
            }
        } else if (glueTypeEnum != null && glueTypeEnum.isScript()) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof ScriptJobHandler
                            && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdatetime() == triggerModel.getGlueUpdatetime())) {
                // change script or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerModel.getJobId(), triggerModel.getGlueUpdatetime(), triggerModel.getGlueSource(), GlueTypeEnum.match(triggerModel.getGlueType()));
            }
        } else {
            return new Result<String>(Result.FAIL_CODE, "glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerModel.getExecutorBlockStrategy(), null);
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new Result<String>(Result.FAIL_CODE, "block strategy effect：" + ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            } else {
                // just queue trigger
            }
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = XxlJobExecutor.registJobThread(triggerModel.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        Result<String> pushResult = jobThread.pushTriggerQueue(triggerModel);
        return pushResult;
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        // kill handlerThread, and create new one
        JobThread jobThread = XxlJobExecutor.loadJobThread(killModel.getJobId());
        if (jobThread != null) {
            XxlJobExecutor.removeJobThread(killModel.getJobId(), "scheduling center kill job.");
            return Result.SUCCESS;
        }

        return new Result<String>(Result.SUCCESS_CODE, "job thread already killed.");
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        // log filename: logPath/yyyy-MM-dd/9999.log
        String logFileName = XxlJobFileAppender.makeLogFileName(new Date(logModel.getLogDateTim()), logModel.getLogId());

        LogResultModel logResultModel = XxlJobFileAppender.readLog(logFileName, logModel.getFromLineNum());
        return new Result<LogResultModel>(logResultModel);
    }

}
