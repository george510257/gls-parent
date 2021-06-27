package com.gls.job.admin.core.alarm.impl;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.constants.JobAdminProperties;
import com.gls.job.admin.core.alarm.JobAlarm;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
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

    private String loadEmailJobAlarmTemplate() {
        return "<h5>监控告警明细: </span>" +
                "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
                "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
                "      <tr>\n" +
                "         <td width=\"20%\" >执行器</td>\n" +
                "         <td width=\"10%\" >任务ID</td>\n" +
                "         <td width=\"20%\" >任务描述</td>\n" +
                "         <td width=\"10%\" >告警类型</td>\n" +
                "         <td width=\"40%\" >告警内容</td>\n" +
                "      </tr>\n" +
                "   </thead>\n" +
                "   <tbody>\n" +
                "      <tr>\n" +
                "         <td>{0}</td>\n" +
                "         <td>{1}</td>\n" +
                "         <td>{2}</td>\n" +
                "         <td>调度失败</td>\n" +
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
            if (!jobLog.getTriggerCode().equals(Result.SUCCESS_CODE)) {
                alarmContent += "<br>TriggerMsg=<br>" + jobLog.getTriggerMsg();
            }
            if (jobLog.getHandleCode() > 0 && !jobLog.getHandleCode().equals(Result.SUCCESS_CODE)) {
                alarmContent += "<br>HandleCode=" + jobLog.getHandleMsg();
            }
            // email info
            String personal = "分布式任务调度平台GLS-JOB";
            String title = "任务调度中心监控报警";
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
