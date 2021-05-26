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
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */

@Component
public class JobAdminConfig implements InitializingBean, DisposableBean {

    private static JobAdminConfig adminConfig = null;
    private JobScheduler jobScheduler;

    // ---------------------- JobScheduler ----------------------
    // conf
    @Value("${xxl.job.i18n}")
    private String i18n;
    @Value("${xxl.job.accessToken}")
    private String accessToken;
    @Value("${spring.mail.from}")
    private String emailFrom;

    // ---------------------- JobScheduler ----------------------
    @Value("${xxl.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;
    @Value("${xxl.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;
    @Value("${xxl.job.logretentiondays}")
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

    public JobLogDao getXxlJobLogDao() {
        return jobLogDao;
    }

    public JobInfoDao getXxlJobInfoDao() {
        return jobInfoDao;
    }

    public JobRegistryDao getXxlJobRegistryDao() {
        return jobRegistryDao;
    }

    public JobGroupDao getXxlJobGroupDao() {
        return jobGroupDao;
    }

    public JobLogReportDao getXxlJobLogReportDao() {
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
