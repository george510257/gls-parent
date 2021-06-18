package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.JobLogEntity;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * doAlarm
     *
     * @param jobInfo
     * @param jobLog
     * @return
     */
    boolean doAlarm(JobLogEntity jobLogEntity);

}
