package com.xxl.job.admin.core.thread;

import com.gls.job.admin.core.alarm.JobAlarmHolder;
import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * job monitor instance
 *
 * @author xuxueli 2015-9-1 18:05:56
 */
@Slf4j
public class JobFailMonitorHelper {

    private static JobFailMonitorHelper instance = new JobFailMonitorHelper();
    @Resource
    public I18nHelper i18nHelper;
    private Thread monitorThread;
    // ---------------------- monitor ----------------------
    private volatile boolean toStop = false;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobInfoDao jobInfoDao;
    @Resource
    private JobAlarmHolder jobAlarmHolder;

    public static JobFailMonitorHelper getInstance() {
        return instance;
    }

    public void start() {
        monitorThread = new Thread(() -> {

            // monitor
            while (!toStop) {
                try {

                    List<Long> failLogIds = jobLogDao.findFailJobLogIds(1000);
                    if (failLogIds != null && !failLogIds.isEmpty()) {
                        for (long failLogId : failLogIds) {

                            // lock log
                            int lockRet = jobLogDao.updateAlarmStatus(failLogId, 0, -1);
                            if (lockRet < 1) {
                                continue;
                            }
                            JobLog log = jobLogDao.load(failLogId);
                            JobInfo info = jobInfoDao.loadById(log.getJobId());

                            // 1、fail retry monitor
                            if (log.getExecutorFailRetryCount() > 0) {
                                JobTriggerPoolHelper.trigger(log.getJobId(), TriggerType.RETRY, (log.getExecutorFailRetryCount() - 1), log.getExecutorShardingParam(), log.getExecutorParam(), null);
                                String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>" + i18nHelper.getString("job_conf_trigger_type_retry") + "<<<<<<<<<<< </span><br>";
                                log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                                jobLogDao.updateTriggerInfo(log);
                            }

                            // 2、fail alarm monitor
                            int newAlarmStatus = 0;        // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
                            if (info != null && info.getAlarmEmail() != null && info.getAlarmEmail().trim().length() > 0) {
                                boolean alarmResult = jobAlarmHolder.alarm(info, log);
                                newAlarmStatus = alarmResult ? 2 : 3;
                            } else {
                                newAlarmStatus = 1;
                            }

                            jobLogDao.updateAlarmStatus(failLogId, -1, newAlarmStatus);
                        }
                    }

                } catch (Exception e) {
                    if (!toStop) {
                        log.error(">>>>>>>>>>> gls-job, job fail monitor thread error:{}", e);
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    if (!toStop) {
                        log.error(e.getMessage(), e);
                    }
                }

            }

            log.info(">>>>>>>>>>> gls-job, job fail monitor thread stop");

        });
        monitorThread.setDaemon(true);
        monitorThread.setName("gls-job, admin JobFailMonitorHelper");
        monitorThread.start();
    }

    public void toStop() {
        toStop = true;
        // interrupt and wait
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
