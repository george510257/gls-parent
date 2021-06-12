package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.util.DateUtil;
import com.gls.job.executor.context.JobContext;
import com.gls.job.executor.context.JobContextHolder;
import com.gls.job.executor.web.repository.JobLogRepository;
import com.gls.job.executor.web.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author george
 */
@Slf4j
@Service
public class JobLogServiceImpl implements JobLogService {

    private final JobContextHolder jobContextHolder = JobContextHolder.getInstance();
    @Resource
    private JobLogRepository jobLogRepository;

    @Override
    public void logFileClean(int logRetentionDays) {
        jobLogRepository.logFileClean(logRetentionDays);
    }

    @Override
    public LogResultModel readLog(LogModel logModel) {
        String logFileName = getLogFileName(logModel.getLogDateTime(), logModel.getLogId());
        return jobLogRepository.readLog(logFileName, logModel.getFromLineNum());
    }

    @Override
    public String getLogFileName(Date logDateTime, Long logId) {
        return jobLogRepository.getLogFileName(logDateTime, logId);
    }

    @Override
    public boolean log(String appendLogPattern, Object... appendLogArguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(appendLogPattern, appendLogArguments);
        String appendLog = ft.getMessage();
        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    @Override
    public boolean log(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String appendLog = stringWriter.toString();
        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    /**
     * append log
     *
     * @param callInfo
     * @param appendLog
     */
    private boolean logDetail(StackTraceElement callInfo, String appendLog) {
        JobContext jobContext = jobContextHolder.get();
        if (jobContext == null) {
            return false;
        }
        String formatAppendLog = DateUtil.formatDateTime(new Date()) + " " +
                "[" + callInfo.getClassName() + "#" + callInfo.getMethodName() + "]" + "-" +
                "[" + callInfo.getLineNumber() + "]" + "-" +
                "[" + Thread.currentThread().getName() + "]" + " " +
                (appendLog != null ? appendLog : "");
        String logFileName = jobContext.getJobLogFileName();
        if (logFileName != null && logFileName.trim().length() > 0) {
            jobLogRepository.saveLog(logFileName, formatAppendLog);
            return true;
        } else {
            log.info(">>>>>>>>>>> {}", formatAppendLog);
            return false;
        }
    }
}
