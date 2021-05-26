package com.gls.job.admin.core.alarm;

import com.gls.job.admin.core.model.JobInfo;
import com.gls.job.admin.core.model.JobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(JobInfo info, JobLog jobLog);

}
