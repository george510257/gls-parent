package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.base.BaseThread;
import com.gls.job.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobCompleteThread extends BaseThread {

    @Resource
    private JobLogService jobLogService;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private I18nHelper i18nHelper;

    @Override
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(50);
    }

    @Override
    protected void doExecute() throws Exception {
        // 任务结果丢失处理：调度记录停留在 "运行中" 状态超过10min，且对应执行器心跳注册失败不在线，则将本地调度主动标记失败；
        Date losedTime = DateUtil.addMinutes(new Date(), -10);
        List<Long> losedJobIds = jobLogDao.findLostJobIds(losedTime);

        if (losedJobIds != null && losedJobIds.size() > 0) {
            for (Long logId : losedJobIds) {

                JobLog jobLog = new JobLog();
                jobLog.setId(logId);

                jobLog.setHandleTime(new Date());
                jobLog.setHandleCode(Result.FAIL_CODE);
                jobLog.setHandleMsg(i18nHelper.getString("job_log_lost_fail"));

                jobLogService.updateHandleInfoAndFinish(jobLog);
            }

        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(60);
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobCompleteThread destroy");
    }
}
