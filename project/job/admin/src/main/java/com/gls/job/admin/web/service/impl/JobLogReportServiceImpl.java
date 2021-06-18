package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.job.admin.web.repository.JobLogReportRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobLogReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author george
 */
@Slf4j
@Service
public class JobLogReportServiceImpl implements JobLogReportService {

    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogReportRepository jobLogReportRepository;
    @Resource
    private JobAdminProperties jobAdminProperties;

    @Override
    public long doJobLogReport(long lastCleanLogTime) {
        //refresh JobLogReport
        for (int i = 0; i < 3; i++) {
            refreshJobLogReport(i);
        }
        return cleanJobLog(lastCleanLogTime);
    }

    private long cleanJobLog(long lastCleanLogTime) {
        // 2、log-clean: switch open & once each day
        if (jobAdminProperties.getLogRetentionDays() > 0 && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

            // expire-time
            Calendar expiredDay = Calendar.getInstance();
            expiredDay.add(Calendar.DAY_OF_MONTH, -1 * jobAdminProperties.getLogRetentionDays());
            expiredDay.set(Calendar.HOUR_OF_DAY, 0);
            expiredDay.set(Calendar.MINUTE, 0);
            expiredDay.set(Calendar.SECOND, 0);
            expiredDay.set(Calendar.MILLISECOND, 0);
            Date clearBeforeTime = expiredDay.getTime();

            // clean expired log

            jobLogRepository.getPage(null, null, clearBeforeTime, null, null, 0, 1000).get().forEach(jobLogEntity -> {
                jobLogRepository.delete(jobLogEntity);
            });

            // update clean time
            lastCleanLogTime = System.currentTimeMillis();
        }
        return lastCleanLogTime;
    }

    private void refreshJobLogReport(int i) {
        // today
        Calendar itemDay = Calendar.getInstance();
        itemDay.add(Calendar.DAY_OF_MONTH, -i);
        itemDay.set(Calendar.HOUR_OF_DAY, 0);
        itemDay.set(Calendar.MINUTE, 0);
        itemDay.set(Calendar.SECOND, 0);
        itemDay.set(Calendar.MILLISECOND, 0);

        Date todayFrom = itemDay.getTime();

        itemDay.set(Calendar.HOUR_OF_DAY, 23);
        itemDay.set(Calendar.MINUTE, 59);
        itemDay.set(Calendar.SECOND, 59);
        itemDay.set(Calendar.MILLISECOND, 999);

        Date todayTo = itemDay.getTime();

        // refresh log-report every minute

        JobLogReportEntity jobLogReportEntity = new JobLogReportEntity();
        jobLogReportEntity.setTriggerDay(todayFrom);
        Optional<JobLogReportEntity> optional = jobLogReportRepository.getByTriggerDay(todayFrom);
        if (optional.isPresent()) {
            jobLogReportEntity = optional.get();
        }

        Map<String, Integer> result = jobLogRepository.getLogReport(todayFrom, todayTo);
        if (!ObjectUtils.isEmpty(result)) {
            int triggerDayCount = result.get("triggerDayCount");
            int triggerDayCountRunning = result.get("triggerDayCountRunning");
            int triggerDayCountSuc = result.get("triggerDayCountSuc");
            int triggerDayCountFail = triggerDayCount - triggerDayCountRunning - triggerDayCountSuc;

            jobLogReportEntity.setRunningCount(triggerDayCountRunning);
            jobLogReportEntity.setSucCount(triggerDayCountSuc);
            jobLogReportEntity.setFailCount(triggerDayCountFail);
        }
        // do refresh
        jobLogReportRepository.save(jobLogReportEntity);
    }
}
