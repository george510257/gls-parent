package com.gls.job.admin.core.alarm;

import com.gls.job.admin.core.model.XxlJobInfo;
import com.gls.job.admin.core.model.XxlJobLog;

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
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
