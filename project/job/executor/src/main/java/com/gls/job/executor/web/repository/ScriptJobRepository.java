package com.gls.job.executor.web.repository;

import com.gls.job.core.api.model.enums.GlueType;

import java.util.Date;

/**
 * @author georg
 */
public interface ScriptJobRepository {

    /**
     * 保存文件路径
     *
     * @param jobId
     * @param glueUpdateTime
     * @param glueSource
     * @param glueType
     * @return
     */
    String saveScriptJob(Long jobId, Date glueUpdateTime, String glueSource, GlueType glueType);
}
