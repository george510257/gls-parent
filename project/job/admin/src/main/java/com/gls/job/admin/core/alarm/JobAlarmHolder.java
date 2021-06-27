package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.JobLogEntity;
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
     * @param jobLogEntity
     * @return
     */
    public boolean alarm(JobLogEntity jobLogEntity) {
        AtomicBoolean result = new AtomicBoolean(false);
        this.loadAll().forEach(jobAlarm -> {
            result.set(jobAlarm.doAlarm(jobLogEntity));
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
