package com.gls.job.admin.core.complete;

import com.gls.job.admin.core.conf.XxlJobAdminConfig;
import com.gls.job.admin.core.model.XxlJobInfo;
import com.gls.job.admin.core.model.XxlJobLog;
import com.gls.job.admin.core.thread.JobTriggerPoolHelper;
import com.gls.job.admin.core.trigger.TriggerTypeEnum;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.context.XxlJobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author george 2020-10-30 20:43:10
 */
public class XxlJobCompleter {
    private static Logger logger = LoggerFactory.getLogger(XxlJobCompleter.class);

    /**
     * common fresh handle entrance (limit only once)
     *
     * @param glsJobLog
     * @return
     */
    public static int updateHandleInfoAndFinish(XxlJobLog glsJobLog) {

        // finish
        finishJob(glsJobLog);

        // text最大64kb 避免长度过长
        if (glsJobLog.getHandleMsg().length() > 15000) {
            glsJobLog.setHandleMsg(glsJobLog.getHandleMsg().substring(0, 15000));
        }

        // fresh handle
        return XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfo(glsJobLog);
    }

    /**
     * do somethind to finish job
     */
    private static void finishJob(XxlJobLog glsJobLog) {

        // 1、handle success, to trigger child job
        String triggerChildMsg = null;
        if (XxlJobContext.HANDLE_CODE_SUCCESS == glsJobLog.getHandleCode()) {
            XxlJobInfo glsJobInfo = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(glsJobLog.getJobId());
            if (glsJobInfo != null && glsJobInfo.getChildJobId() != null && glsJobInfo.getChildJobId().trim().length() > 0) {
                triggerChildMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>" + I18nUtil.getString("jobconf_trigger_child_run") + "<<<<<<<<<<< </span><br>";

                String[] childJobIds = glsJobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    int childJobId = (childJobIds[i] != null && childJobIds[i].trim().length() > 0 && isNumeric(childJobIds[i])) ? Integer.valueOf(childJobIds[i]) : -1;
                    if (childJobId > 0) {

                        JobTriggerPoolHelper.trigger(childJobId, TriggerTypeEnum.PARENT, -1, null, null, null);
                        Result<String> triggerChildResult = Result.SUCCESS;

                        // add msg
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg1"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode() == Result.SUCCESS_CODE ? I18nUtil.getString("system_success") : I18nUtil.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg2"),
                                (i + 1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        if (triggerChildMsg != null) {
            glsJobLog.setHandleMsg(glsJobLog.getHandleMsg() + triggerChildMsg);
        }

        // 2、fix_delay trigger next
        // on the way

    }

    private static boolean isNumeric(String str) {
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
