package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.core.server.JobTriggerPoolHelper;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.JobLog;
import com.gls.job.admin.web.entity.enums.TriggerType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class JobFailMonitorThread extends Thread {

    @Setter
    private volatile boolean toStop = false;

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
                            JobTriggerPoolHelper.trigger(log.getJobId(), TriggerType.RETRY, (log.getExecutorFailRetryCount() - 1), log.getExecutorShardingParam(), log.getExecutorParam(), null);
                            String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>" + I18nUtil.getString("jobconf_trigger_type_retry") + "<<<<<<<<<<< </span><br>";
                            log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                            JobAdminConfig.getAdminConfig().getJobLogDao().updateTriggerInfo(log);
                        }

                        // 2、fail alarm monitor
                        int newAlarmStatus = 0;
                        // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
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
    }
}
