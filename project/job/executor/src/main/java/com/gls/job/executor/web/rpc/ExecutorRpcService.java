package com.gls.job.executor.web.rpc;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.executor.core.glue.GlueFactory;
import com.gls.job.executor.core.handler.IJobHandler;
import com.gls.job.executor.core.handler.impl.GlueJobHandler;
import com.gls.job.executor.core.handler.impl.ScriptJobHandler;
import com.gls.job.executor.core.helper.XxlJobFileHelper;
import com.gls.job.executor.core.holder.JobThreadHolder;
import com.gls.job.executor.core.thread.JobThread;
import com.gls.job.executor.web.repository.JobHandlerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author george
 */
@Slf4j
@DubboService
public class ExecutorRpcService implements ExecutorApi {

    @Resource
    private JobThreadHolder jobThreadRepository;

    @Resource
    private JobHandlerRepository jobHandlerRepository;

    @Override
    public Result<String> beat() {
        return Result.SUCCESS;
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {
        JobThread jobThread = jobThreadRepository.load(idleBeatModel.getJobId());
        if (jobThread != null && jobThread.isRunningOrHasQueue()) {
            return Result.SUCCESS;
        }
        return new Result<>(Result.FAIL_CODE, "job thread is running or has trigger queue.");
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        // load old：jobHandler + jobThread
        JobThread jobThread = jobThreadRepository.load(triggerModel.getJobId());
        IJobHandler jobHandler = jobThread != null ? jobThread.getHandler() : null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        GlueType glueTypeEnum = triggerModel.getGlueType();
        if (GlueType.BEAN == glueTypeEnum) {

            // new jobHandler
            IJobHandler newJobHandler = jobHandlerRepository.loadJobHandler(triggerModel.getExecutorHandler());

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

        } else if (GlueType.GLUE_GROOVY == glueTypeEnum) {

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
            jobThread = new JobThread(triggerModel.getJobId(), jobHandler);
            jobThreadRepository.regist(triggerModel.getJobId(), jobThread, removeOldReason);
        }

        // push data to queue
        return jobThread.pushTriggerQueue(triggerModel);
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        // kill handlerThread, and create new one
        JobThread jobThread = jobThreadRepository.load(killModel.getJobId());
        if (jobThread != null) {
            jobThreadRepository.remove(killModel.getJobId(), "scheduling center kill job.");
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
