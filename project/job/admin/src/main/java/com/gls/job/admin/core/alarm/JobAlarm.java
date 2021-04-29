package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.JobLog;

/**
 * @author george 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    boolean doAlarm(JobInfo info, JobLog jobLog);

}
