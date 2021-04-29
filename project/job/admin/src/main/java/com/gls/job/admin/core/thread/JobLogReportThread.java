package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.web.entity.JobLogReport;
import lombok.Setter;
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
public class JobLogReportThread extends Thread {

    @Setter
    private volatile boolean toStop = false;

    @Override
    public void run() {
        // last clean log time
        long lastCleanLogTime = 0;

        while (!toStop) {

            // 1、log-report refresh: refresh log report in 3 days
            try {

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

            } catch (Exception e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, job log report thread error:{}", e);
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
                    logIds = JobAdminConfig.getAdminConfig().getJobLogDao().findClearLogIds(0, 0, clearBeforeTime, 0, 1000);
                    if (logIds != null && logIds.size() > 0) {
                        JobAdminConfig.getAdminConfig().getJobLogDao().clearLog(logIds);
                    }
                } while (logIds != null && logIds.size() > 0);

                // update clean time
                lastCleanLogTime = System.currentTimeMillis();
            }

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }

        }
        log.info(">>>>>>>>>>> gls-job, job log report thread stop");
    }
}
