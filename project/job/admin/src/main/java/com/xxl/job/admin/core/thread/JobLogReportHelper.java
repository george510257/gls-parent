package com.xxl.job.admin.core.thread;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.dao.JobLogReportDao;
import com.gls.job.admin.web.model.JobLogReport;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * job log report helper
 *
 * @author xuxueli 2019-11-22
 */
@Slf4j
public class JobLogReportHelper {

    private static final JobLogReportHelper instance = new JobLogReportHelper();
    private Thread logrThread;
    private volatile boolean toStop = false;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobLogReportDao jobLogReportDao;
    @Resource
    private JobAdminProperties jobAdminProperties;

    public static JobLogReportHelper getInstance() {
        return instance;
    }

    public void start() {
        logrThread = new Thread(() -> {

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

                        Map<String, Object> triggerCountMap = jobLogDao.findLogReport(todayFrom, todayTo);
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
                        int ret = jobLogReportDao.update(jobLogReport);
                        if (ret < 1) {
                            jobLogReportDao.save(jobLogReport);
                        }
                    }

                } catch (Exception e) {
                    if (!toStop) {
                        log.error(">>>>>>>>>>> gls-job, job log report thread error:{}", e);
                    }
                }

                // 2、log-clean: switch open & once each day
                if (jobAdminProperties.getLogRetentionDays() > 0
                        && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

                    // expire-time
                    Calendar expiredDay = Calendar.getInstance();
                    expiredDay.add(Calendar.DAY_OF_MONTH, -1 * jobAdminProperties.getLogRetentionDays());
                    expiredDay.set(Calendar.HOUR_OF_DAY, 0);
                    expiredDay.set(Calendar.MINUTE, 0);
                    expiredDay.set(Calendar.SECOND, 0);
                    expiredDay.set(Calendar.MILLISECOND, 0);
                    Date clearBeforeTime = expiredDay.getTime();

                    // clean expired log
                    List<Long> logIds;
                    do {
                        logIds = jobLogDao.findClearLogIds(0L, 0L, clearBeforeTime, 0, 1000);
                        if (logIds != null && logIds.size() > 0) {
                            jobLogDao.clearLog(logIds);
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

        });
        logrThread.setDaemon(true);
        logrThread.setName("gls-job, admin JobLogReportHelper");
        logrThread.start();
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        logrThread.interrupt();
        try {
            logrThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
