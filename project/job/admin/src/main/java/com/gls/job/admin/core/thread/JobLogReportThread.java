package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.XxlJobAdminConfig;
import com.gls.job.admin.web.entity.XxlJobLogReport;
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
                    XxlJobLogReport glsJobLogReport = new XxlJobLogReport();
                    glsJobLogReport.setTriggerDay(todayFrom);
                    glsJobLogReport.setRunningCount(0);
                    glsJobLogReport.setSucCount(0);
                    glsJobLogReport.setFailCount(0);

                    Map<String, Object> triggerCountMap = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findLogReport(todayFrom, todayTo);
                    if (triggerCountMap != null && triggerCountMap.size() > 0) {
                        int triggerDayCount = triggerCountMap.containsKey("triggerDayCount") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCount"))) : 0;
                        int triggerDayCountRunning = triggerCountMap.containsKey("triggerDayCountRunning") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCountRunning"))) : 0;
                        int triggerDayCountSuc = triggerCountMap.containsKey("triggerDayCountSuc") ? Integer.parseInt(String.valueOf(triggerCountMap.get("triggerDayCountSuc"))) : 0;
                        int triggerDayCountFail = triggerDayCount - triggerDayCountRunning - triggerDayCountSuc;

                        glsJobLogReport.setRunningCount(triggerDayCountRunning);
                        glsJobLogReport.setSucCount(triggerDayCountSuc);
                        glsJobLogReport.setFailCount(triggerDayCountFail);
                    }

                    // do refresh
                    int ret = XxlJobAdminConfig.getAdminConfig().getXxlJobLogReportDao().update(glsJobLogReport);
                    if (ret < 1) {
                        XxlJobAdminConfig.getAdminConfig().getXxlJobLogReportDao().save(glsJobLogReport);
                    }
                }

            } catch (Exception e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, job log report thread error:{}", e);
                }
            }

            // 2、log-clean: switch open & once each day
            if (XxlJobAdminConfig.getAdminConfig().getLogretentiondays() > 0
                    && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

                // expire-time
                Calendar expiredDay = Calendar.getInstance();
                expiredDay.add(Calendar.DAY_OF_MONTH, -1 * XxlJobAdminConfig.getAdminConfig().getLogretentiondays());
                expiredDay.set(Calendar.HOUR_OF_DAY, 0);
                expiredDay.set(Calendar.MINUTE, 0);
                expiredDay.set(Calendar.SECOND, 0);
                expiredDay.set(Calendar.MILLISECOND, 0);
                Date clearBeforeTime = expiredDay.getTime();

                // clean expired log
                List<Long> logIds = null;
                do {
                    logIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findClearLogIds(0, 0, clearBeforeTime, 0, 1000);
                    if (logIds != null && logIds.size() > 0) {
                        XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().clearLog(logIds);
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
