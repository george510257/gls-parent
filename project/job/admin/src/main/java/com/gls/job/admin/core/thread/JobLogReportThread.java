package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.web.entity.JobLogReport;
import com.gls.job.core.base.thread.BaseThread;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class JobLogReportThread extends BaseThread {

    private long lastCleanLogTime;

    @Override
    protected void initExecute() throws Exception {
        lastCleanLogTime = 0;
    }

    @Override
    protected void doExecute() throws Exception {
        // 1、log-report refresh: refresh log report in 3 days
        for (int i = 0; i < 3; i++) {

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
            JobLogReport jobLogReport = new JobLogReport();
            jobLogReport.setTriggerDay(todayFrom);
            jobLogReport.setRunningCount(0);
            jobLogReport.setSucCount(0);
            jobLogReport.setFailCount(0);

            Map<String, Object> triggerCountMap = JobAdminConfig.getAdminConfig().getJobLogDao().findLogReport(todayFrom, todayTo);
            if (triggerCountMap != null && triggerCountMap.size() > 0) {
                int triggerDayCount = triggerCountMap.containsKey("triggerDayCount") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCount"))) : 0;
                int triggerDayCountRunning = triggerCountMap.containsKey("triggerDayCountRunning") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCountRunning"))) : 0;
                int triggerDayCountSuc = triggerCountMap.containsKey("triggerDayCountSuc") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCountSuc"))) : 0;
                int triggerDayCountFail = triggerDayCount - triggerDayCountRunning - triggerDayCountSuc;

                jobLogReport.setRunningCount(triggerDayCountRunning);
                jobLogReport.setSucCount(triggerDayCountSuc);
                jobLogReport.setFailCount(triggerDayCountFail);
            }

            // do refresh
            int ret = JobAdminConfig.getAdminConfig().getJobLogReportDao().update(jobLogReport);
            if (ret < 1) {
                JobAdminConfig.getAdminConfig().getJobLogReportDao().save(jobLogReport);
            }
        }
        // 2、log-clean: switch open & once each day
        if (JobAdminConfig.getAdminConfig().getLogretentiondays() > 0
                && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

            // expire-time
            Calendar expiredDay = Calendar.getInstance();
            expiredDay.add(Calendar.DAY_OF_MONTH, -1 * JobAdminConfig.getAdminConfig().getLogretentiondays());
            expiredDay.set(Calendar.HOUR_OF_DAY, 0);
            expiredDay.set(Calendar.MINUTE, 0);
            expiredDay.set(Calendar.SECOND, 0);
            expiredDay.set(Calendar.MILLISECOND, 0);
            Date clearBeforeTime = expiredDay.getTime();

            // clean expired log
            List<Long> logIds = null;
            do {
                logIds = JobAdminConfig.getAdminConfig().getJobLogDao().findClearLogIds(0L, 0L, clearBeforeTime, 0, 1000);
                if (logIds != null && logIds.size() > 0) {
                    JobAdminConfig.getAdminConfig().getJobLogDao().clearLog(logIds);
                }
            } while (logIds != null && logIds.size() > 0);

            // update clean time
            lastCleanLogTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.MINUTES.sleep(1);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, job log report thread stop");
    }
}
