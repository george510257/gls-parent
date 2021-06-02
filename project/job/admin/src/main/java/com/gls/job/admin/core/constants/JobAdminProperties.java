package com.gls.job.admin.core.constants;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * gls-job config
 *
 * @author xuxueli 2017-04-28
 */
@Data
@Component
@ConfigurationProperties(prefix = JobAdminProperties.PREFIX, ignoreInvalidFields = true)
public class JobAdminProperties {

    public static final String PREFIX = FrameworkConstants.BASE_PROPERTIES_PREFIX + ".job.admin";

    private String i18n;
    private String accessToken;
    private String emailFrom;

    private Integer triggerPoolFastMax;
    private Integer triggerPoolSlowMax;
    private Integer logRetentionDays;

}
