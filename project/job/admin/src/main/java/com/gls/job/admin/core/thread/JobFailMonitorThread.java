package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.alarm.JobAlarmer;
import com.gls.job.admin.core.server.JobTriggerPoolHelper;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.JobLog;
import com.gls.job.admin.web.entity.enums.TriggerType;
import com.gls.job.core.base.thread.BaseThread;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class JobFailMonitorThread extends BaseThread {

    @Resource
    private JobLogDao jobLogDao;

    @Resource
    private JobInfoDao jobInfoDao;

    @Resource
    private JobAlarmer jobAlarmer;

    @Override
    protected void initExecute() throws Exception {
    }

    @Override
    protected void doExecute() throws Exception {
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
                    String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>" + I18nUtil.getString("job_conf_trigger_type_retry") + "<<<<<<<<<<< </span><br>";
                    log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                    jobLogDao.updateTriggerInfo(log);
                }

                // 2、fail alarm monitor
                int newAlarmStatus = 0;
                // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
                if (info != null && info.getAlarmEmail() != null && info.getAlarmEmail().trim().length() > 0) {
                    boolean alarmResult = jobAlarmer.alarm(info, log);
                    newAlarmStatus = alarmResult ? 2 : 3;
                } else {
                    newAlarmStatus = 1;
                }

                jobLogDao.updateAlarmStatus(failLogId, -1, newAlarmStatus);
            }
        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(10);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, job fail monitor thread stop");
    }
}
