package com.gls.job.core.constants;

/**
 * @author george
 */
public interface JobConstants {

    int HANDLE_CODE_SUCCESS = 200;
    int HANDLE_CODE_FAIL = 500;
    int HANDLE_CODE_TIMEOUT = 502;

    int BEAT_TIMEOUT = 30;
    int DEAD_TIMEOUT = BEAT_TIMEOUT * 3;

    long PRE_READ_MS = 5000;

    String LOGIN_IDENTITY_KEY = "GLS_JOB_LOGIN_IDENTITY";
}
