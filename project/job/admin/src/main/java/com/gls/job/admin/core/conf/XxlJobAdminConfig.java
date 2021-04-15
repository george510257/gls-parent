package com.gls.job.admin.core.conf;

import com.gls.job.admin.core.alarm.JobAlarmer;
import com.gls.job.admin.core.scheduler.XxlJobScheduler;
import com.gls.job.admin.dao.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * gls-job config
 *
 * @author xuxueli 2017-04-28
 */

@Component
public class XxlJobAdminConfig implements InitializingBean, DisposableBean {

    private static XxlJobAdminConfig adminConfig = null;
    private XxlJobScheduler glsJobScheduler;

    // ---------------------- XxlJobScheduler ----------------------
    // conf
    @Value("${gls.job.i18n}")
    private String i18n;
    @Value("${gls.job.accessToken}")
    private String accessToken;
    @Value("${spring.mail.from}")
    private String emailFrom;

    // ---------------------- XxlJobScheduler ----------------------
    @Value("${gls.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;
    @Value("${gls.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;
    @Value("${gls.job.logretentiondays}")
    private int logretentiondays;
    @Resource
    private XxlJobLogDao glsJobLogDao;
    @Resource
    private XxlJobInfoDao glsJobInfoDao;
    @Resource
    private XxlJobRegistryDao glsJobRegistryDao;

    // dao, service
    @Resource
    private XxlJobGroupDao glsJobGroupDao;
    @Resource
    private XxlJobLogReportDao glsJobLogReportDao;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private DataSource dataSource;
    @Resource
    private JobAlarmer jobAlarmer;

    public static XxlJobAdminConfig getAdminConfig() {
        return adminConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;

        glsJobScheduler = new XxlJobScheduler();
        glsJobScheduler.init();
    }

    @Override
    public void destroy() throws Exception {
        glsJobScheduler.destroy();
    }

    public String getI18n() {
        if (!Arrays.asList("zh_CN", "zh_TC", "en").contains(i18n)) {
            return "zh_CN";
        }
        return i18n;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public int getTriggerPoolFastMax() {
        if (triggerPoolFastMax < 200) {
            return 200;
        }
        return triggerPoolFastMax;
    }

    public int getTriggerPoolSlowMax() {
        if (triggerPoolSlowMax < 100) {
            return 100;
        }
        return triggerPoolSlowMax;
    }

    public int getLogretentiondays() {
        if (logretentiondays < 7) {
            return -1;  // Limit greater than or equal to 7, otherwise close
        }
        return logretentiondays;
    }

    public XxlJobLogDao getXxlJobLogDao() {
        return glsJobLogDao;
    }

    public XxlJobInfoDao getXxlJobInfoDao() {
        return glsJobInfoDao;
    }

    public XxlJobRegistryDao getXxlJobRegistryDao() {
        return glsJobRegistryDao;
    }

    public XxlJobGroupDao getXxlJobGroupDao() {
        return glsJobGroupDao;
    }

    public XxlJobLogReportDao getXxlJobLogReportDao() {
        return glsJobLogReportDao;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JobAlarmer getJobAlarmer() {
        return jobAlarmer;
    }

}
