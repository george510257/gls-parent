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
        JobHandler jobHandler = loadJobHandler(triggerModel);
        String removeOldReason = null;

        if (jobThread != null) {
            if (!jobHandler.equals(jobThread.getHandler())) {
                jobThread = null;
                removeOldReason = "change jobHandler or glue type, and terminate the old job thread.";
            }
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategy blockStrategy = triggerModel.getExecutorBlockStrategy();
            if (ExecutorBlockStrategy.DISCARD_LATER.equals(blockStrategy)) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    throw new Exception("block strategy effect：" + ExecutorBlockStrategy.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategy.COVER_EARLY.equals(blockStrategy)) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    jobThread = null;
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategy.COVER_EARLY.getTitle();
                }
            }  // just queue trigger
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = loadJobThread(triggerModel.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        return jobThread.pushTriggerQueue(triggerModel);
    }

    @Override
    public boolean kill(Long jobId) {
        JobThread jobThread = jobThreadRepository.remove(jobId, "scheduling center kill job.");
        return jobThread != null;
    }

    private JobThread loadJobThread(Long jobId, JobHandler jobHandler, String removeOldReason) {
        JobThread jobThread = new JobThread(jobId, jobHandler);
        jobThreadRepository.regist(jobId, jobThread, removeOldReason);
        return jobThread;
    }

    private JobHandler loadJobHandler(TriggerModel triggerModel) throws Exception {
        GlueType glueType = triggerModel.getGlueType();
        if (glueType == null) {
            throw new Exception("glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }
        switch (glueType) {
            case BEAN: {
                JobHandler jobHandler = jobHandlerRepository.loadJobHandler(triggerModel.getExecutorHandler());
                if (jobHandler == null) {
                    throw new Exception("job handler [" + triggerModel.getExecutorHandler() + "] not found.");
                }
                return jobHandler;
            }
            case GLUE_GROOVY: {
                JobHandler jobHandler = GlueFactory.getInstance().loadNewInstance(triggerModel.getGlueSource());
                return new GlueJobHandler(jobHandler, triggerModel.getGlueUpdateTime());
            }
            default: {
                return new ScriptJobHandler(triggerModel.getJobId(), triggerModel.getGlueUpdateTime(), triggerModel.getGlueSource(), triggerModel.getGlueType());
            }
        }
    }
}
