package com.gls.job.admin.core.conf;

import com.gls.job.admin.web.service.JobSchedulerService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * gls-job config
 *
 * @author george 2017-04-28
 */

@Component
public class JobAdminConfig implements InitializingBean, DisposableBean {

    private JobSchedulerService jobScheduler;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        jobScheduler = new JobSchedulerService();
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

}
