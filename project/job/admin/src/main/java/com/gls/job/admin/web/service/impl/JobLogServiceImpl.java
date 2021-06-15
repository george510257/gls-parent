package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.constants.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service
public class JobLogServiceImpl implements JobLogService {
    @Resource
    public I18nHelper i18nHelper;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobInfoDao jobInfoDao;
    @Resource
    private JobTriggerService jobTriggerService;

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
        StringBuilder triggerChildMsg = null;
        if (JobConstants.HANDLE_CODE_SUCCESS == jobLog.getHandleCode()) {
            JobInfo jobInfo = jobInfoDao.loadById(jobLog.getJobId());
            if (jobInfo != null && jobInfo.getChildJobId() != null && jobInfo.getChildJobId().trim().length() > 0) {
                triggerChildMsg = new StringBuilder("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>" + i18nHelper.getString("job_conf_trigger_child_run") + "<<<<<<<<<<< </span><br>");

                String[] childJobIds = jobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    long childJobId = (childJobIds[i] != null && childJobIds[i].trim().length() > 0 && isNumeric(childJobIds[i])) ? Long.parseLong(childJobIds[i]) : -1;
                    if (childJobId > 0) {

                        jobTriggerService.trigger(childJobId, TriggerType.PARENT, -1, null, null, null);
                        Result<String> triggerChildResult = Result.SUCCESS;

                        // add msg
                        triggerChildMsg.append(MessageFormat.format(i18nHelper.getString("job_conf_callback_child_msg1"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode() == Result.SUCCESS_CODE ? i18nHelper.getString("system_success") : i18nHelper.getString("system_fail")),
                                triggerChildResult.getMsg()));
                    } else {
                        triggerChildMsg.append(MessageFormat.format(i18nHelper.getString("job_conf_callback_child_msg2"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i]));
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

    @Async
    @Override
    public void callback(List<CallbackModel> callbackModels) {

        for (CallbackModel callbackModel : callbackModels) {
            Result<String> callbackResult = callback(callbackModel);
            log.debug(">>>>>>>>> JobApiController.callback {}, callbackModel={}, callbackResult={}",
                    (callbackResult.getCode() == Result.SUCCESS_CODE ? "success" : "fail"), callbackModel, callbackResult);
        }

    }

    private Result<String> callback(CallbackModel callbackModel) {
        // valid log item
        JobLog log = jobLogDao.load(callbackModel.getLogId());
        if (log == null) {
            return new Result<>(Result.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            return new Result<>(Result.FAIL_CODE, "log repeate callback.");
            // avoid repeat callback, trigger child job etc
        }

        // handle msg
        StringBuilder handleMsg = new StringBuilder();
        if (log.getHandleMsg() != null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (callbackModel.getHandleMsg() != null) {
            handleMsg.append(callbackModel.getHandleMsg());
        }

        // success, save log
        log.setHandleTime(new Date());
        log.setHandleCode(callbackModel.getHandleCode());
        log.setHandleMsg(handleMsg.toString());
        updateHandleInfoAndFinish(log);

        return Result.SUCCESS;
    }
}
