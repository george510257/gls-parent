package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.model.XxlJobInfo;
import com.gls.job.admin.web.model.XxlJobLog;

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
    boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
