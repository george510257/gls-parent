package com.gls.job.admin.web.validator;

import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.admin.core.support.CronExpression;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.core.constants.GlueType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class JobInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return JobInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobInfo jobInfo = (JobInfo) target;
        // JobGroup
        if (ObjectUtils.isEmpty(jobInfo.getJobGroup())) {
            errors.rejectValue("jobGroup", "jobInfo.jobGroup.required", "请选择执行器");
        }
        // ScheduleType
        if (jobInfo.getScheduleType() == ScheduleType.CRON) {
            if (ObjectUtils.isEmpty(jobInfo.getScheduleConf()) || CronExpression.isValidExpression(jobInfo.getScheduleConf())) {
                errors.rejectValue("scheduleConf", "jobInfo.scheduleConf.cron", "Cron非法");
            }
        } else if (jobInfo.getScheduleType() == ScheduleType.FIX_RATE) {
            if (ObjectUtils.isEmpty(jobInfo.getScheduleConf())) {
                errors.rejectValue("scheduleType", "jobInfo.scheduleConf.type", "调度类型非法");
            }
            try {
                int fixSecond = Integer.parseInt(jobInfo.getScheduleConf());
                if (fixSecond < 1) {
                    errors.rejectValue("scheduleType", "jobInfo.scheduleConf.type", "调度类型非法");
                }
            } catch (Exception e) {
                errors.rejectValue("scheduleType", "jobInfo.scheduleConf.type", "调度类型非法");
            }
        }
        // GlueType
        if (jobInfo.getGlueType() == GlueType.BEAN) {
            if (ObjectUtils.isEmpty(jobInfo.getExecutorHandler())) {
                errors.rejectValue("executorHandler", "jobInfo.executorHandler.required", "请填写JobHandler");
            }
        }
    }
}
