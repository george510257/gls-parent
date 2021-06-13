package com.gls.job.admin.core.constants;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Data
@Component
@ConfigurationProperties(prefix = JobAdminProperties.PREFIX, ignoreInvalidFields = true)
public class JobAdminProperties {

    public static final String PREFIX = FrameworkConstants.BASE_PROPERTIES_PREFIX + ".job.admin";

    private String i18n = "zh_CN";
    private String accessToken;
    private String emailFrom;

    private int triggerPoolFastMax = 200;
    private int triggerPoolSlowMax = 100;
    private int logRetentionDays = 30;
}
