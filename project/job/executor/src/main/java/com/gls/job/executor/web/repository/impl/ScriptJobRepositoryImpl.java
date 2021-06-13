package com.gls.job.executor.web.repository.impl;

import com.gls.job.core.api.model.enums.GlueType;
import com.gls.job.core.util.FileUtil;
import com.gls.job.executor.core.constants.JobExecutorProperties;
import com.gls.job.executor.web.repository.ScriptJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author georg
 */
@Slf4j
@Repository
public class ScriptJobRepositoryImpl implements ScriptJobRepository {

    @Resource
    private JobExecutorProperties jobExecutorProperties;

    @Override
    public String saveScriptJob(Long jobId, Date glueUpdateTime, String glueSource, GlueType glueType) {
        deleteOldScriptJob(jobId);
        String scriptFileName = getBaseFileName()
                .replace("{jobId}", jobId.toString())
                .replace("{updateTime}", glueUpdateTime.toString())
                .replace("{suffix}", glueType.getSuffix());
        try {
            IOUtils.write(glueSource.getBytes(StandardCharsets.UTF_8), new FileOutputStream(scriptFileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return scriptFileName;
    }

    private void deleteOldScriptJob(Long jobId) {
        List<String> fileNames = FileUtil.findFiles(jobExecutorProperties.getGlueSrcPath());
        fileNames.forEach(fileName -> {
            if (fileName.startsWith(getBaseFileName().replace("{jobId}", jobId.toString()).replace("_{updateTime}{suffix}", ""))) {
                FileUtil.deleteFile(fileName);
            }
        });
    }

    private String getBaseFileName() {
        return jobExecutorProperties.getGlueSrcPath().concat("/{jobId}_{updateTime}{suffix}");
    }
}
