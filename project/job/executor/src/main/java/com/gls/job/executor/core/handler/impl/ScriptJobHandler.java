package com.gls.job.executor.core.handler.impl;

import com.gls.job.core.constants.GlueType;
import com.gls.job.executor.core.context.JobContextHolder;
import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.handler.script.ScriptHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author xuxueli
 * @date 17/4/27
 */
@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ScriptJobHandler implements JobHandler {
    private final String scriptFileName;
    private final Date glueUpdateTime;
    private final GlueType glueType;
    private final JobContextHolder jobContextHolder;

    @Override
    public void execute() throws Exception {
        if (!glueType.isScript()) {
            jobContextHolder.handleFail("glueType[" + glueType + "] invalid.");
            return;
        }
        // cmd
        String cmd = glueType.getCmd();
        // log file
        String logFileName = jobContextHolder.get().getJobLogFileName();
        // script params：0=param、1=分片序号、2=分片总数
        String[] scriptParams = new String[3];
        scriptParams[0] = jobContextHolder.get().getJobParam();
        scriptParams[1] = String.valueOf(jobContextHolder.get().getShardIndex());
        scriptParams[2] = String.valueOf(jobContextHolder.get().getShardTotal());
        // invoke
        log.info("----------- script file:" + scriptFileName + " -----------");
        int exitValue = ScriptHelper.execToFile(cmd, scriptFileName, logFileName, scriptParams);
        if (exitValue == 0) {
            jobContextHolder.handleSuccess();
        } else {
            jobContextHolder.handleFail("script exit value(" + exitValue + ") is failed");
        }
    }
}
