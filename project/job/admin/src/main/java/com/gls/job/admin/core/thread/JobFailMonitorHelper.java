package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.core.trigger.TriggerTypeEnum;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * job monitor instance
 *
 * @author xuxueli 2015-9-1 18:05:56
 */
public class JobFailMonitorHelper {
    private static Logger logger = LoggerFactory.getLogger(JobFailMonitorHelper.class);

    private static JobFailMonitorHelper instance = new JobFailMonitorHelper();
    private Thread monitorThread;

    // ---------------------- monitor ----------------------
    private volatile boolean toStop = false;

    public static JobFailMonitorHelper getInstance() {
        return instance;
    }

    public void start() {
        monitorThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // monitor
                while (!toStop) {
                    try {

                        List<Long> failLogIds = JobAdminConfig.getAdminConfig().getJobLogDao().findFailJobLogIds(1000);
                        if (failLogIds != null && !failLogIds.isEmpty()) {
                            for (long failLogId : failLogIds) {

                                // lock log
                                int lockRet = JobAdminConfig.getAdminConfig().getJobLogDao().updateAlarmStatus(failLogId, 0, -1);
                                if (lockRet < 1) {
                                    continue;
                                }
                                JobLog log = JobAdminConfig.getAdminConfig().getJobLogDao().load(failLogId);
                                JobInfo info = JobAdminConfig.getAdminConfig().getJobInfoDao().loadById(log.getJobId());

                                // 1、fail retry monitor
                                if (log.getExecutorFailRetryCount() > 0) {
                                    JobTriggerPoolHelper.trigger(log.getJobId(), TriggerTypeEnum.RETRY, (log.getExecutorFailRetryCount() - 1), log.getExecutorShardingParam(), log.getExecutorParam(), null);
                                    String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>" + I18nUtil.getString("jobconf_trigger_type_retry") + "<<<<<<<<<<< </span><br>";
                                    log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                                    JobAdminConfig.getAdminConfig().getJobLogDao().updateTriggerInfo(log);
                                }

                                // 2、fail alarm monitor
                                int newAlarmStatus = 0;        // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
                                if (info != null && info.getAlarmEmail() != null && info.getAlarmEmail().trim().length() > 0) {
                                    boolean alarmResult = JobAdminConfig.getAdminConfig().getJobAlarmer().alarm(info, log);
                                    newAlarmStatus = alarmResult ? 2 : 3;
                                } else {
                                    newAlarmStatus = 1;
                                }

                                JobAdminConfig.getAdminConfig().getJobLogDao().updateAlarmStatus(failLogId, -1, newAlarmStatus);
                            }
                        }

                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(">>>>>>>>>>> gls-job, job fail monitor thread error:{}", e);
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                }

                logger.info(">>>>>>>>>>> gls-job, job fail monitor thread stop");

            }
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
            logger.error(e.getMessage(), e);
        }
    }

}
