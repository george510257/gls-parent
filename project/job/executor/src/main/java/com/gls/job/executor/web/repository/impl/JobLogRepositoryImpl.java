package com.gls.job.executor.web.repository.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.executor.core.constants.JobExecutorProperties;
import com.gls.job.executor.web.repository.JobLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Repository
public class JobLogRepositoryImpl implements JobLogRepository {
    @Resource
    private JobExecutorProperties jobExecutorProperties;

    @Override
    public void logFileClean() {
        List<String> fileNames = FileUtil.listFileNames(jobExecutorProperties.getLogBasePath());
        fileNames.forEach(fileName -> {
            String name = fileName.replace(jobExecutorProperties.getLogBasePath().concat("/"), "");
            Date date = DateUtil.parseDate(name);
            if (DateUtil.offsetDay(date, jobExecutorProperties.getLogRetentionDays() + 1).getTime() < System.currentTimeMillis()) {
                FileUtil.del(fileName);
            }
        });
    }

    @Override
    public LogResultModel readLog(String logFileName, Integer fromLineNum) {
        try {
            List<String> data = IOUtils.readLines(new InputStreamReader(new FileInputStream(logFileName), StandardCharsets.UTF_8));
            StringBuilder logContent = new StringBuilder();
            for (int i = fromLineNum; i < data.size(); i++) {
                logContent.append(data.get(i)).append("\n");
            }
            return new LogResultModel(fromLineNum, data.size(), logContent.toString(), false);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new LogResultModel(fromLineNum, 0, "readLog fail, logFile not found", true);
    }

    @Override
    public String getLogFileName(Date logDateTime, Long logId) {
        return getBaseFileName().replace("{yyyy-MM-dd}", DateUtil.formatDate(logDateTime)).replace("{logId}", logId.toString());
    }

    @Override
    public void saveLog(String logFileName, String appendLog) {
        try {
            IOUtils.write(appendLog.concat("\n").getBytes(StandardCharsets.UTF_8), new FileOutputStream(logFileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getBaseFileName() {
        return jobExecutorProperties.getLogBasePath().concat("/{yyyy-MM-dd}/{logId}.log");
    }
}
