package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.XxlJobInfo;
import com.gls.job.admin.web.entity.XxlJobLog;

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
    boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
