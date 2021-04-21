package com.gls.job.executor.handler.impl;

import com.gls.job.core.enums.GlueTypeEnum;
import com.gls.job.executor.context.XxlJobContext;
import com.gls.job.executor.handler.IJobHandler;
import com.gls.job.executor.helper.ScriptHelper;
import com.gls.job.executor.helper.XxlJobFileHelper;
import com.gls.job.executor.helper.XxlJobHelper;

import java.io.File;

/**
 * @author george
 */
public class ScriptJobHandler extends IJobHandler {

    private final int jobId;
    private final long glueUpdateTime;
    private final String glueSource;
    private final GlueTypeEnum glueType;

    public ScriptJobHandler(int jobId, long glueUpdateTime, String glueSource, GlueTypeEnum glueType) {
        this.jobId = jobId;
        this.glueUpdateTime = glueUpdateTime;
        this.glueSource = glueSource;
        this.glueType = glueType;

        // clean old script file
        File glueSrcPath = new File(XxlJobFileHelper.getGlueSrcPath());
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

    public long getGlueUpdateTime() {
        return glueUpdateTime;
    }

    @Override
    public void execute() throws Exception {

        if (!glueType.isScript()) {
            XxlJobHelper.handleFail("glueType[" + glueType + "] invalid.");
            return;
        }

        // cmd
        String cmd = glueType.getCmd();

        // make script file
        String scriptFileName = XxlJobFileHelper.getGlueSrcPath()
                .concat(File.separator)
                .concat(String.valueOf(jobId))
                .concat("_")
                .concat(String.valueOf(glueUpdateTime))
                .concat(glueType.getSuffix());
        File scriptFile = new File(scriptFileName);
        if (!scriptFile.exists()) {
            ScriptHelper.markScriptFile(scriptFileName, glueSource);
        }

        // log file
        String logFileName = XxlJobContext.getXxlJobContext().getJobLogFileName();

        // script params：0=param、1=分片序号、2=分片总数
        String[] scriptParams = new String[3];
        scriptParams[0] = XxlJobHelper.getJobParam();
        scriptParams[1] = String.valueOf(XxlJobContext.getXxlJobContext().getShardIndex());
        scriptParams[2] = String.valueOf(XxlJobContext.getXxlJobContext().getShardTotal());

        // invoke
        XxlJobHelper.log("----------- script file:" + scriptFileName + " -----------");
        int exitValue = ScriptHelper.execToFile(cmd, scriptFileName, logFileName, scriptParams);

        if (exitValue == 0) {
            XxlJobHelper.handleSuccess();
        } else {
            XxlJobHelper.handleFail("script exit value(" + exitValue + ") is failed");
        }

    }

}
