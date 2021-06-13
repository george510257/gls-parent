package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.model.XxlJobInfo;
import com.gls.job.admin.web.model.XxlJobLog;
import com.gls.job.core.base.BaseHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author george
 */
@Component
public class JobAlarmHolder extends BaseHolder<String, JobAlarm> {

    @Resource
    private Map<String, JobAlarm> jobAlarmMap;

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean alarm(XxlJobInfo info, XxlJobLog jobLog) {
        AtomicBoolean result = new AtomicBoolean(false);
        this.loadAll().forEach(jobAlarm -> {
            result.set(jobAlarm.doAlarm(info, jobLog));
        });
        return result.get();
    }

    @Override
    protected void delete(JobAlarm oldValue, String reason) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        regist(jobAlarmMap);
    }
}
