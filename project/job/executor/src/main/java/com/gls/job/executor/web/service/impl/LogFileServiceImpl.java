package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.util.FileUtil;
import com.gls.job.executor.core.constants.ExecutorProperties;
import com.gls.job.executor.core.helper.JobFileHelper;
import com.gls.job.executor.web.service.LogFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author george
 */
@Slf4j
@Service
public class LogFileServiceImpl implements LogFileService {

    @Resource
    private ExecutorProperties executorProperties;

    @Override
    public void cleanLogFile() {
        // clean log dir, over logRetentionDays
        File[] childDirs = new File(JobFileHelper.getLogPath()).listFiles();
        if (childDirs != null && childDirs.length > 0) {

            // today
            Calendar todayCal = Calendar.getInstance();
            todayCal.set(Calendar.HOUR_OF_DAY, 0);
            todayCal.set(Calendar.MINUTE, 0);
            todayCal.set(Calendar.SECOND, 0);
            todayCal.set(Calendar.MILLISECOND, 0);

            Date todayDate = todayCal.getTime();

            for (File childFile : childDirs) {

                // valid
                if (!childFile.isDirectory()) {
                    continue;
                }
                if (!childFile.getName().contains("-")) {
                    continue;
                }
                // file create date
                Date logFileCreateDate = null;
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    logFileCreateDate = simpleDateFormat.parse(childFile.getName());
                } catch (ParseException e) {
                    log.error(e.getMessage(), e);
                }
                if (logFileCreateDate == null) {
                    continue;
                }
                if ((todayDate.getTime() - logFileCreateDate.getTime()) >= executorProperties.getLogRetentionDays() * (24 * 60 * 60 * 1000)) {
                    FileUtil.deleteRecursively(childFile);
                }
            }
        }
    }

    @Override
    public LogResultModel readLogFile(LogModel logModel) {
        // log filename: logPath/yyyy-MM-dd/9999.log
        String logFileName = JobFileHelper.makeLogFileName(logModel.getLogDateTime(), logModel.getLogId());
        return JobFileHelper.readLog(logFileName, logModel.getFromLineNumber());
    }
}
