package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLogEntity
     * @return
     */
    public boolean doAlarm(JobInfoEntity info, JobLogEntity jobLogEntity);

}
