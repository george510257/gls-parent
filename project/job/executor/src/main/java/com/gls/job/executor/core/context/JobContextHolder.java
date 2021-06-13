package com.gls.job.executor.core.context;

import com.gls.job.core.base.BaseThreadLocalHolder;
import com.gls.job.core.constants.JobConstants;

/**
 * @author george
 */
public class JobContextHolder extends BaseThreadLocalHolder<JobContext> {

    private static final JobContextHolder INSTANCE = new JobContextHolder();

    private JobContextHolder() {
    }

    public static JobContextHolder getInstance() {
        return INSTANCE;
    }

    /**
     * handle success
     *
     * @return
     */
    public boolean handleSuccess() {
        return handleResult(JobConstants.HANDLE_CODE_SUCCESS, null);
    }

    /**
     * handle success with log msg
     *
     * @param handleMsg
     * @return
     */
    public boolean handleSuccess(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_SUCCESS, handleMsg);
    }

    /**
     * handle fail
     *
     * @return
     */
    public boolean handleFail() {
        return handleResult(JobConstants.HANDLE_CODE_FAIL, null);
    }

    /**
     * handle fail with log msg
     *
     * @param handleMsg
     * @return
     */
    public boolean handleFail(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_FAIL, handleMsg);
    }

    /**
     * handle timeout
     *
     * @return
     */
    public boolean handleTimeout() {
        return handleResult(JobConstants.HANDLE_CODE_TIMEOUT, null);
    }

    /**
     * handle timeout with log msg
     *
     * @param handleMsg
     * @return
     */
    public boolean handleTimeout(String handleMsg) {
        return handleResult(JobConstants.HANDLE_CODE_TIMEOUT, handleMsg);
    }

    /**
     * @param handleCode 200 : success
     *                   500 : fail
     *                   502 : timeout
     * @param handleMsg
     * @return
     */
    public boolean handleResult(int handleCode, String handleMsg) {
        JobContext jobContext = get();
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
