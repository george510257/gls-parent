package com.gls.job.admin.core.alarm;

import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Component
public class JobAlarmManager {

    @Resource
    private Map<String, JobAlarm> jobAlarmMap;

    /**
     * Job Alarm
     *
     * @param jobInfoEntity
     * @param jobLogEntity
     * @return
     */
    public boolean alarm(JobInfoEntity jobInfoEntity, JobLogEntity jobLogEntity) {
        boolean result = false;
        if (jobAlarmMap != null && jobAlarmMap.size() > 0) {
            result = true;
            // success means all-success
            for (JobAlarm alarm : jobAlarmMap.values()) {
                boolean resultItem = false;
                try {
                    resultItem = alarm.doAlarm(jobInfoEntity, jobLogEntity);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                if (!resultItem) {
                    result = false;
                }
            }
        }

        return result;
    }

}
