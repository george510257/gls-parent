package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.executor.core.glue.GlueFactory;
import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.handler.impl.GlueJobHandler;
import com.gls.job.executor.core.handler.impl.ScriptJobHandler;
import com.gls.job.executor.core.holder.JobThreadHolder;
import com.gls.job.executor.core.thread.JobThread;
import com.gls.job.executor.web.repository.JobHandlerRepository;
import com.gls.job.executor.web.service.TriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@Service
public class TriggerServiceImpl implements TriggerService {

    @Resource
    private JobThreadHolder jobThreadRepository;

    @Resource
    private JobHandlerRepository jobHandlerRepository;

    @Override
    public boolean idleBeat(Long jobId) {
        JobThread jobThread = jobThreadRepository.load(jobId);
        return jobThread != null && jobThread.isRunningOrHasQueue();
    }

    @Override
    public boolean run(TriggerModel triggerModel) throws Exception {
        // load old：jobHandler + jobThread
        JobThread jobThread = jobThreadRepository.load(triggerModel.getJobId());
        JobHandler jobHandler = jobThread != null ? jobThread.getHandler() : null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        GlueType glueTypeEnum = triggerModel.getGlueType();
        if (GlueType.BEAN == glueTypeEnum) {

            // new jobHandler
            JobHandler newJobHandler = jobHandlerRepository.loadJobHandler(triggerModel.getExecutorHandler());

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
                    throw new Exception("job handler [" + triggerModel.getExecutorHandler() + "] not found.");
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
                JobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerModel.getGlueSource());
                jobHandler = new GlueJobHandler(originJobHandler, triggerModel.getGlueUpdateTime());
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
            throw new Exception("glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategy blockStrategy = triggerModel.getExecutorBlockStrategy();
            if (ExecutorBlockStrategy.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    throw new Exception("block strategy effect：" + ExecutorBlockStrategy.DISCARD_LATER.getTitle());
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
    public boolean kill(Long jobId) {
        JobThread jobThread = jobThreadRepository.remove(jobId, "scheduling center kill job.");
        return jobThread != null;
    }
}
