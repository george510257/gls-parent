package com.gls.job.admin.core.conf;

import com.gls.job.admin.core.alarm.JobAlarmer;
import com.gls.job.admin.core.scheduler.JobScheduler;
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
public class JobAdminConfig implements InitializingBean, DisposableBean {

    private static JobAdminConfig adminConfig = null;
    private JobScheduler jobScheduler;

    // ---------------------- JobScheduler ----------------------
    // conf
    @Value("${gls.job.i18n}")
    private String i18n;
    @Value("${gls.job.accessToken}")
    private String accessToken;
    @Value("${spring.mail.from}")
    private String emailFrom;

    // ---------------------- JobScheduler ----------------------
    @Value("${gls.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;
    @Value("${gls.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;
    @Value("${gls.job.logretentiondays}")
    private int logretentiondays;
    @Resource
    private JobLogDao jobLogDao;
    @Resource
    private JobInfoDao jobInfoDao;
    @Resource
    private JobRegistryDao jobRegistryDao;

    // dao, service
    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private JobLogReportDao jobLogReportDao;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private DataSource dataSource;
    @Resource
    private JobAlarmer jobAlarmer;

    public static JobAdminConfig getAdminConfig() {
        return adminConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;

        jobScheduler = new JobScheduler();
        jobScheduler.init();
    }

    @Override
    public void destroy() throws Exception {
        jobScheduler.destroy();
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

    public JobLogDao getJobLogDao() {
        return jobLogDao;
    }

    public JobInfoDao getJobInfoDao() {
        return jobInfoDao;
    }

    public JobRegistryDao getJobRegistryDao() {
        return jobRegistryDao;
    }

    public JobGroupDao getJobGroupDao() {
        return jobGroupDao;
    }

    public JobLogReportDao getJobLogReportDao() {
        return jobLogReportDao;
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
