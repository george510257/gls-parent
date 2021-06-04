package com.xxl.job.core.handler.impl;

import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.core.executor.context.JobContextHolder;
import com.xxl.job.core.context.JobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.util.ScriptUtil;

import java.io.File;
import java.util.Date;

/**
 * @author xuxueli
 * @date 17/4/27
 */
public class ScriptJobHandler extends IJobHandler {

    private final Long jobId;
    private final Date glueUpdateTime;
    private final String glueSource;
    private final GlueType glueType;

    public ScriptJobHandler(Long jobId, Date glueUpdateTime, String glueSource, GlueType glueType) {
        this.jobId = jobId;
        this.glueUpdateTime = glueUpdateTime;
        this.glueSource = glueSource;
        this.glueType = glueType;

        // clean old script file
        File glueSrcPath = new File(XxlJobFileAppender.getGlueSrcPath());
        if (glueSrcPath.exists()) {
            File[] glueSrcFileList = glueSrcPath.listFiles();
            if (glueSrcFileList != null && glueSrcFileList.length > 0) {
                for (File glueSrcFileItem : glueSrcFileList) {
                    if (glueSrcFileItem.getName().startsWith(jobId + "_")) {
                        glueSrcFileItem.delete();
                    }
                }
            }
        }

    }

    public Date getGlueUpdateTime() {
        return glueUpdateTime;
    }

    @Override
    public void execute() throws Exception {

        if (!glueType.isScript()) {
            JobHelper.handleFail("glueType[" + glueType + "] invalid.");
            return;
        }

        // cmd
        String cmd = glueType.getCmd();

        // make script file
        String scriptFileName = XxlJobFileAppender.getGlueSrcPath()
                .concat(File.separator)
                .concat(String.valueOf(jobId))
                .concat("_")
                .concat(String.valueOf(glueUpdateTime))
                .concat(glueType.getSuffix());
        File scriptFile = new File(scriptFileName);
        if (!scriptFile.exists()) {
            ScriptUtil.markScriptFile(scriptFileName, glueSource);
        }

        // log file
        String logFileName = JobContextHolder.getJobContext().getJobLogFileName();

        // script params：0=param、1=分片序号、2=分片总数
        String[] scriptParams = new String[3];
        scriptParams[0] = JobContextHolder.getJobContext().getJobParam();
        scriptParams[1] = String.valueOf(JobContextHolder.getJobContext().getShardIndex());
        scriptParams[2] = String.valueOf(JobContextHolder.getJobContext().getShardTotal());

        // invoke
        JobHelper.log("----------- script file:" + scriptFileName + " -----------");
        int exitValue = ScriptUtil.execToFile(cmd, scriptFileName, logFileName, scriptParams);

        if (exitValue == 0) {
            JobHelper.handleSuccess();
        } else {
            JobHelper.handleFail("script exit value(" + exitValue + ") is failed");
        }

    }

}
