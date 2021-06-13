package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * doAlarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    boolean doAlarm(JobInfo info, JobLog jobLog);

}
