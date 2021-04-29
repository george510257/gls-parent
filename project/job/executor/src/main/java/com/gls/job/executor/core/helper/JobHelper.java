package com.gls.job.executor.core.helper;

import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.util.DateUtil;
import com.gls.job.executor.core.holder.JobContextHolder;
import com.gls.job.executor.web.model.JobContextModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * helper for gls-job
 *
 * @author george 2020-11-05
 */
@Slf4j
public class JobHelper {

    // ---------------------- base info ----------------------

    /**
     * current JobId
     *
     * @return
     */
    public static long getJobId() {
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
        JobContextModel jobContext = JobContextHolder.getInstance().get();
        if (jobContext == null) {
            return false;
        }

        String formatAppendLog = DateUtil.formatDateTime(new Date()) + " " +
                "[" + callInfo.getClassName() + "#" + callInfo.getMethodName() + "]" + "-" +
                "[" + callInfo.getLineNumber() + "]" + "-" +
                "[" + Thread.currentThread().getName() + "]" + " " +
                (appendLog != null ? appendLog : "");

        // appendlog
        String logFileName = jobContext.getJobLogFileName();

        if (logFileName != null && logFileName.trim().length() > 0) {
            JobFileHelper.appendLog(logFileName, formatAppendLog);
            return true;
        } else {
            log.info(">>>>>>>>>>> {}", formatAppendLog);
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
        return handleResult(JobConstants.HANDLE_CODE_SUCCESS, null);
    }

    /**
     * handle success with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleSuccess(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_SUCCESS, handleMsg);
    }

    /**
     * handle fail
     *
     * @return
     */
    public static boolean handleFail() {
        return handleResult(JobConstants.HANDLE_CODE_FAIL, null);
    }

    /**
     * handle fail with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleFail(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_FAIL, handleMsg);
    }

    /**
     * handle timeout
     *
     * @return
     */
    public static boolean handleTimeout() {
        return handleResult(JobConstants.HANDLE_CODE_TIMEOUT, null);
    }

    /**
     * handle timeout with log msg
     *
     * @param handleMsg
     * @return
     */
    public static boolean handleTimeout(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_TIMEOUT, handleMsg);
    }

    /**
     * @param handleCode 200 : success
     *                   500 : fail
     *                   502 : timeout
     * @param handleMsg
     * @return
     */
    public static boolean handleResult(int handleCode, String handleMsg) {
        JobContextModel jobContext = JobContextHolder.getInstance().get();
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
