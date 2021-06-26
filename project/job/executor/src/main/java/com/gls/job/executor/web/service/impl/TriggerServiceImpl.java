package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.constants.ExecutorBlockStrategy;
import com.gls.job.core.constants.GlueType;
import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.handler.JobHandlerHolder;
import com.gls.job.executor.core.handler.builder.GlueBuilder;
import com.gls.job.executor.core.handler.impl.GlueJobHandler;
import com.gls.job.executor.core.handler.impl.ScriptJobHandler;
import com.gls.job.executor.core.thread.JobThread;
import com.gls.job.executor.core.thread.JobThreadHolder;
import com.gls.job.executor.web.repository.ScriptJobRepository;
import com.gls.job.executor.web.service.TriggerService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author george
 */
@Service
public class TriggerServiceImpl implements TriggerService {
    @Resource
    private JobThreadHolder jobThreadHolder;
    @Resource
    private JobHandlerHolder jobHandlerHolder;

    @Resource
    private GlueBuilder glueBuilder;

    @Resource
    private ScriptJobRepository scriptJobRepository;

    @Override
    public boolean idleBeat(Long jobId) {
        JobThread jobThread = jobThreadHolder.load(jobId);
        return jobThread != null && jobThread.isRunningOrHasQueue();
    }

    @Override
    public void runTrigger(TriggerModel triggerModel) throws Exception {
        JobThread jobThread = jobThreadHolder.load(triggerModel.getJobId());
        JobHandler newJobHandler = loadJobHandlerByTriggerModel(triggerModel);
        String removeOldReason = null;
        if (jobThread != null) {
            JobHandler oldJobHandler = jobThread.getJobHandler();
            if (!oldJobHandler.equals(newJobHandler)) {
                jobThread = null;
                removeOldReason = "change job source or glue type, and terminate the old job thread.";
            }
        }
        // executor block strategy
        if (jobThread != null) {
            if (ExecutorBlockStrategy.DISCARD_LATER.equals(triggerModel.getExecutorBlockStrategy())) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    throw new Exception("block strategy effect：" + ExecutorBlockStrategy.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategy.COVER_EARLY.equals(triggerModel.getExecutorBlockStrategy())) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    jobThread = null;
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategy.COVER_EARLY.getTitle();
                }
            }
            // just queue trigger
        }
        if (jobThread == null) {
            jobThread = jobThreadHolder.registByJobHandler(triggerModel.getJobId(), newJobHandler, removeOldReason);
        }
        jobThread.getTriggerQueueHolder().push(triggerModel);
    }

    @Override
    public String removeJobThread(Long jobId) {
        JobThread jobThread = jobThreadHolder.load(jobId);
        if (jobThread != null) {
            jobThreadHolder.remove(jobId, "scheduling center kill job.");
            return "";
        }
        return "job thread already killed.";
    }

    private JobHandler loadJobHandlerByTriggerModel(TriggerModel triggerModel) throws Exception {
        GlueType glueType = triggerModel.getGlueType();
        JobHandler jobHandler = null;
        if (GlueType.BEAN.equals(glueType)) {
            jobHandler = jobHandlerHolder.load(triggerModel.getExecutorHandler());
            if (ObjectUtils.isEmpty(jobHandler)) {
                throw new Exception("job handler [" + triggerModel.getExecutorHandler() + "] not found.");
            }
        } else if (GlueType.GLUE_GROOVY.equals(glueType)) {
            jobHandler = loadGlueJobHandler(triggerModel.getGlueSource(), triggerModel.getGlueUpdateTime());
        } else if (glueType != null && glueType.isScript()) {
            jobHandler = loadScriptJobHandler(triggerModel.getJobId(), triggerModel.getGlueUpdateTime(), triggerModel.getGlueSource(), triggerModel.getGlueType());
        }
        if (ObjectUtils.isEmpty(jobHandler)) {
            throw new Exception("glueType[" + triggerModel.getGlueType() + "] is not valid.");
        }
        return jobHandler;
    }

    private ScriptJobHandler loadScriptJobHandler(Long jobId, Date glueUpdateTime, String glueSource, GlueType glueType) {
        String scriptFileName = scriptJobRepository.saveScriptJob(jobId, glueUpdateTime, glueSource, glueType);
        return new ScriptJobHandler(scriptFileName, glueUpdateTime, glueType);
    }

    private GlueJobHandler loadGlueJobHandler(String glueSource, Date glueUpdateTime) throws Exception {
        return new GlueJobHandler(glueBuilder.loadNewInstance(glueSource), glueUpdateTime);
    }
}
