package com.gls.job.core.context;

import com.gls.job.core.log.JobFileAppender;
import com.gls.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * helper for gls-job
 *
 * @author xuxueli 2020-11-05
 */
public class JobHelper {

    // ---------------------- base info ----------------------

    private static Logger logger = LoggerFactory.getLogger("gls-job logger");

    /**
     * current JobId
     *
     * @return
     */
    public static long getJobId() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }

        return jobContext.getJobId();
    }

    // ---------------------- for log ----------------------

    /**
     * current JobParam
     *
     * @return
     */
    public static String getJobParam() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return null;
        }

        return jobContext.getJobParam();
    }

    // ---------------------- for shard ----------------------

    /**
     * current JobLogFileName
     *
     * @return
     */
    public static String getJobLogFileName() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return null;
        }

        return jobContext.getJobLogFileName();
    }

    /**
     * current ShardIndex
     *
     * @return
     */
    public static int getShardIndex() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }

        return jobContext.getShardIndex();
    }

    // ---------------------- tool for log ----------------------

    /**
     * current ShardTotal
     *
     * @return
     */
    public static int getShardTotal() {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return -1;
        }

        return jobContext.getShardTotal();
    }

    /**
     * append log with pattern
     *
     * @param appendLogPattern   like "aaa {} bbb {} ccc"
     * @param appendLogArguments like "111, true"
     */
    public static boolean log(String appendLogPattern, Object... appendLogArguments) {

        FormattingTuple ft = MessageFormatter.arrayFormat(appendLogPattern, appendLogArguments);
        String appendLog = ft.getMessage();

        /*appendLog = appendLogPattern;
        if (appendLogArguments!=null && appendLogArguments.length>0) {
            appendLog = MessageFormat.format(appendLogPattern, appendLogArguments);
        }*/

        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        return logDetail(callInfo, appendLog);
    }

    /**
     * append exception stack
     *
     * @param e
     */
    public static boolean log(Throwable e) {

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
    private static boolean logDetail(StackTraceElement callInfo, String appendLog) {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return false;
        }

        /*// "yyyy-MM-dd HH:mm:ss [ClassName]-[MethodName]-[LineNumber]-[ThreadName] log";
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement callInfo = stackTraceElements[1];*/

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(DateUtil.formatDateTime(new Date())).append(" ")
                .append("[" + callInfo.getClassName() + "#" + callInfo.getMethodName() + "]").append("-")
                .append("[" + callInfo.getLineNumber() + "]").append("-")
                .append("[" + Thread.currentThread().getName() + "]").append(" ")
                .append(appendLog != null ? appendLog : "");
        String formatAppendLog = stringBuffer.toString();

        // appendlog
        String logFileName = jobContext.getJobLogFileName();

        if (logFileName != null && logFileName.trim().length() > 0) {
            JobFileAppender.appendLog(logFileName, formatAppendLog);
            return true;
        } else {
            logger.info(">>>>>>>>>>> {}", formatAppendLog);
            return false;
        }
    }

    // ---------------------- tool for handleResult ----------------------

    /**
     * handle success
     *
     * @return
     */
    public static boolean handleSuccess() {
        return handleResult(JobContext.HANDLE_COCE_SUCCESS, null);
    }

    /**
     * handle success with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleSuccess(String handleMsg) {
        return handleResult(JobContext.HANDLE_COCE_SUCCESS, handleMsg);
    }

    /**
     * handle fail
     *
     * @return
     */
    public static boolean handleFail() {
        return handleResult(JobContext.HANDLE_COCE_FAIL, null);
    }

    /**
     * handle fail with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleFail(String handleMsg) {
        return handleResult(JobContext.HANDLE_COCE_FAIL, handleMsg);
    }

    /**
     * handle timeout
     *
     * @return
     */
    public static boolean handleTimeout() {
        return handleResult(JobContext.HANDLE_COCE_TIMEOUT, null);
    }

    /**
     * handle timeout with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleTimeout(String handleMsg) {
        return handleResult(JobContext.HANDLE_COCE_TIMEOUT, handleMsg);
    }

    /**
     * @param handleCode 200 : success
     *                   500 : fail
     *                   502 : timeout
     * @param handleMsg
     * @return
     */
    public static boolean handleResult(int handleCode, String handleMsg) {
        JobContext jobContext = JobContext.getJobContext();
        if (jobContext == null) {
            return false;
        }

        jobContext.setHandleCode(handleCode);
        if (handleMsg != null) {
            jobContext.setHandleMsg(handleMsg);
        }
        return true;
    }

}