package com.gls.job.admin.core.alarm.impl;

import com.gls.job.admin.core.alarm.JobAlarm;
import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.core.biz.model.ReturnT;
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

    /**
     * load email job alarm template
     *
     * @return
     */
    private String loadEmailJobAlarmTemplate() {
        return "<h5>" + I18nUtil.getString("job_conf_monitor_detail") + "：</span>" +
                "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
                "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
                "      <tr>\n" +
                "         <td width=\"20%\" >" + I18nUtil.getString("job_info_field_jobgroup") + "</td>\n" +
                "         <td width=\"10%\" >" + I18nUtil.getString("job_info_field_id") + "</td>\n" +
                "         <td width=\"20%\" >" + I18nUtil.getString("job_info_field_jobdesc") + "</td>\n" +
                "         <td width=\"10%\" >" + I18nUtil.getString("job_conf_monitor_alarm_title") + "</td>\n" +
                "         <td width=\"40%\" >" + I18nUtil.getString("job_conf_monitor_alarm_content") + "</td>\n" +
                "      </tr>\n" +
                "   </thead>\n" +
                "   <tbody>\n" +
                "      <tr>\n" +
                "         <td>{0}</td>\n" +
                "         <td>{1}</td>\n" +
                "         <td>{2}</td>\n" +
                "         <td>" + I18nUtil.getString("job_conf_monitor_alarm_type") + "</td>\n" +
                "         <td>{3}</td>\n" +
                "      </tr>\n" +
                "   </tbody>\n" +
                "</table>";
    }

    /**
     * fail alarm
     *
     * @param jobLogEntity
     */
    @Override
    public boolean doAlarm(JobInfoEntity info, JobLogEntity jobLogEntity) {
        boolean alarmResult = true;

        // send monitor email
        if (info != null && info.getAlarmEmail() != null && info.getAlarmEmail().trim().length() > 0) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + jobLogEntity.getId();
            if (jobLogEntity.getTriggerCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>TriggerMsg=<br>" + jobLogEntity.getTriggerMsg();
            }
            if (jobLogEntity.getHandleCode() > 0 && jobLogEntity.getHandleCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>HandleCode=" + jobLogEntity.getHandleMsg();
            }

            // email info
            JobGroupEntity group = info.getJobGroup();
            String personal = I18nUtil.getString("admin_name_full");
            String title = I18nUtil.getString("job_conf_monitor");
            String content = MessageFormat.format(loadEmailJobAlarmTemplate(),
                    group != null ? group.getTitle() : "null",
                    info.getId(),
                    info.getJobDesc(),
                    alarmContent);

            Set<String> emailSet = new HashSet<>(Arrays.asList(info.getAlarmEmail().split(",")));
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
                    log.error(">>>>>>>>>>> gls-job, job fail alarm email send error, JobLogId:{}", jobLogEntity.getId(), e);

                    alarmResult = false;
                }

            }
        }

        return alarmResult;
    }

}
