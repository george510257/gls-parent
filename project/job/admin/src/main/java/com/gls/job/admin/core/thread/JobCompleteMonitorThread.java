package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.complete.XxlJobCompleter;
import com.gls.job.admin.core.conf.XxlJobAdminConfig;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.XxlJobLog;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.util.DateUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author georg
 */
@Slf4j
public class JobCompleteMonitorThread extends Thread {

    @Setter
    private volatile boolean toStop = false;

    @Override
    public void run() {
        // wait for JobTriggerPoolHelper-init
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            if (!toStop) {
                log.error(e.getMessage(), e);
            }
        }

        // monitor
        while (!toStop) {
            try {
                // 任务结果丢失处理：调度记录停留在 "运行中" 状态超过10min，且对应执行器心跳注册失败不在线，则将本地调度主动标记失败；
                Date losedTime = DateUtil.addMinutes(new Date(), -10);
                List<Long> losedJobIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findLostJobIds(losedTime);

                if (losedJobIds != null && losedJobIds.size() > 0) {
                    for (Long logId : losedJobIds) {

                        XxlJobLog jobLog = new XxlJobLog();
                        jobLog.setId(logId);

                        jobLog.setHandleTime(new Date());
                        jobLog.setHandleCode(Result.FAIL_CODE);
                        jobLog.setHandleMsg(I18nUtil.getString("joblog_lost_fail"));

                        XxlJobCompleter.updateHandleInfoAndFinish(jobLog);
                    }

                }
            } catch (Exception e) {
                if (!toStop) {
                    log.error(">>>>>>>>>>> gls-job, job fail monitor thread error:{}", e);
                }
            }

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }

        }

        log.info(">>>>>>>>>>> gls-job, JobLosedMonitorHelper stop");
    }
}
