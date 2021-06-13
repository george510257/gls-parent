package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobCompleterService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.constants.JobConstants;
import com.xxl.job.admin.core.thread.JobTriggerPoolHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author george
 */
@Slf4j
@Service
public class JobCompleterServiceImpl implements JobCompleterService {
    @Resource
    public I18nHelper i18nHelper;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobInfoDao jobInfoDao;

    /**
     * common fresh handle entrance (limit only once)
     *
     * @param jobLog
     * @return
     */
    @Override
    public void updateHandleInfoAndFinish(JobLog jobLog) {

        // finish
        finishJob(jobLog);

        // text最大64kb 避免长度过长
        if (jobLog.getHandleMsg().length() > 15000) {
            jobLog.setHandleMsg(jobLog.getHandleMsg().substring(0, 15000));
        }

        // fresh handle
        jobLogDao.updateHandleInfo(jobLog);
    }

    /**
     * do somethind to finish job
     */
    private void finishJob(JobLog jobLog) {

        // 1、handle success, to trigger child job
        String triggerChildMsg = null;
        if (JobConstants.HANDLE_CODE_SUCCESS == jobLog.getHandleCode()) {
            JobInfo jobInfo = jobInfoDao.loadById(jobLog.getJobId());
            if (jobInfo != null && jobInfo.getChildJobId() != null && jobInfo.getChildJobId().trim().length() > 0) {
                triggerChildMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>" + i18nHelper.getString("job_conf_trigger_child_run") + "<<<<<<<<<<< </span><br>";

                String[] childJobIds = jobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    long childJobId = (childJobIds[i] != null && childJobIds[i].trim().length() > 0 && isNumeric(childJobIds[i])) ? Long.parseLong(childJobIds[i]) : -1;
                    if (childJobId > 0) {

                        JobTriggerPoolHelper.trigger(childJobId, TriggerType.PARENT, -1, null, null, null);
                        Result<String> triggerChildResult = Result.SUCCESS;

                        // add msg
                        triggerChildMsg += MessageFormat.format(i18nHelper.getString("job_conf_callback_child_msg1"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode() == Result.SUCCESS_CODE ? i18nHelper.getString("system_success") : i18nHelper.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        triggerChildMsg += MessageFormat.format(i18nHelper.getString("job_conf_callback_child_msg2"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        if (triggerChildMsg != null) {
            jobLog.setHandleMsg(jobLog.getHandleMsg() + triggerChildMsg);
        }

        // 2、fix_delay trigger next
        // on the way

    }

    private boolean isNumeric(String str) {
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
