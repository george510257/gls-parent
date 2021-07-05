package com.gls.job.admin.core.support;

import cn.hutool.core.date.DateUtil;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.constants.ScheduleType;
import com.gls.job.admin.web.model.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
public class JobScheduleHelper {
    public static void refreshNextValidTime(JobInfo jobInfo, Date date) {
        Date nextValidTime = generateNextValidTime(jobInfo, date);
        if (nextValidTime != null) {
            jobInfo.setTriggerLastTime(jobInfo.getTriggerNextTime());
            jobInfo.setTriggerNextTime(nextValidTime.getTime());
        } else {
            jobInfo.setTriggerStatus(0);
            jobInfo.setTriggerLastTime(0);
            jobInfo.setTriggerNextTime(0);
            log.warn(">>>>>>>>>>> gls-job, refreshNextValidTime fail for job: jobId={}, scheduleType={}, scheduleConf={}",
                    jobInfo.getId(), jobInfo.getScheduleType(), jobInfo.getScheduleConf());
        }
    }

    public static Date generateNextValidTime(JobInfo jobInfo, Date date) {
        ScheduleType scheduleType = jobInfo.getScheduleType();
        if (ScheduleType.CRON == scheduleType) {
            try {
                return new CronExpression(jobInfo.getScheduleConf()).getNextValidTimeAfter(date);
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
                throw new GlsException("调度类型非法");
            }
        } else if (ScheduleType.FIX_RATE == scheduleType) {
            return new Date(date.getTime() + Integer.parseInt(jobInfo.getScheduleConf()) * 1000L);
        }
        return null;
    }

    public static List<String> nextTriggerTime(String scheduleType, String scheduleConf, int count) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setScheduleType(ScheduleType.valueOf(scheduleType));
        jobInfo.setScheduleConf(scheduleConf);
        List<String> result = new ArrayList<>();
        Date lastTime = new Date();
        for (int i = 0; i < count; i++) {
            lastTime = generateNextValidTime(jobInfo, lastTime);
            if (lastTime != null) {
                result.add(DateUtil.formatDateTime(lastTime));
            } else {
                break;
            }
        }
        return result;
    }
}
