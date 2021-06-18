package com.gls.job.admin.core.alarm.impl;

import com.gls.job.admin.core.alarm.JobAlarm;
import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.core.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * job alarm by email
 *
 * @author xuxueli 2020-01-19
 */
@Slf4j
@Component
public class EmailJobAlarm implements JobAlarm {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private I18nHelper i18nHelper;

    private String loadEmailJobAlarmTemplate() {
        return "<h5>" + i18nHelper.getString("job_conf_monitor_detail") + "：</span>" +
                "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
                "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
                "      <tr>\n" +
                "         <td width=\"20%\" >" + i18nHelper.getString("job_info_field_jobgroup") + "</td>\n" +
                "         <td width=\"10%\" >" + i18nHelper.getString("job_info_field_id") + "</td>\n" +
                "         <td width=\"20%\" >" + i18nHelper.getString("job_info_field_jobdesc") + "</td>\n" +
                "         <td width=\"10%\" >" + i18nHelper.getString("job_conf_monitor_alarm_title") + "</td>\n" +
                "         <td width=\"40%\" >" + i18nHelper.getString("job_conf_monitor_alarm_content") + "</td>\n" +
                "      </tr>\n" +
                "   </thead>\n" +
                "   <tbody>\n" +
                "      <tr>\n" +
                "         <td>{0}</td>\n" +
                "         <td>{1}</td>\n" +
                "         <td>{2}</td>\n" +
                "         <td>" + i18nHelper.getString("job_conf_monitor_alarm_type") + "</td>\n" +
                "         <td>{3}</td>\n" +
                "      </tr>\n" +
                "   </tbody>\n" +
                "</table>";
    }

    @Override
    public boolean doAlarm(JobLogEntity jobLog) {

        JobInfoEntity jobInfo = jobLog.getJobInfo();
        JobGroupEntity jobGroup = jobInfo.getJobGroup();
        boolean alarmResult = true;

        // send monitor email
        if (jobInfo.getAlarmEmail() != null && jobInfo.getAlarmEmail().trim().length() > 0) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + jobLog.getId();
            if (jobLog.getTriggerCode() != Result.SUCCESS_CODE) {
                alarmContent += "<br>TriggerMsg=<br>" + jobLog.getTriggerMsg();
            }
            if (jobLog.getHandleCode() > 0 && jobLog.getHandleCode() != Result.SUCCESS_CODE) {
                alarmContent += "<br>HandleCode=" + jobLog.getHandleMsg();
            }

            // email info
            String personal = i18nHelper.getString("admin_name_full");
            String title = i18nHelper.getString("job_conf_monitor");
            String content = MessageFormat.format(loadEmailJobAlarmTemplate(),
                    jobGroup != null ? jobGroup.getTitle() : "null",
                    jobInfo.getId(),
                    jobInfo.getJobDesc(),
                    alarmContent);

            Set<String> emailSet = new HashSet<>(Arrays.asList(jobInfo.getAlarmEmail().split(",")));
            for (String email : emailSet) {

                // make mail
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();

                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(jobAdminProperties.getEmailFrom(), personal);
                    helper.setTo(email);
                    helper.setSubject(title);
                    helper.setText(content, true);

                    mailSender.send(mimeMessage);
                } catch (Exception e) {
                    log.error(">>>>>>>>>>> gls-job, job fail alarm email send error, JobLogId:{}", jobLog.getId(), e);

                    alarmResult = false;
                }

            }
        }

        return alarmResult;
    }

}
