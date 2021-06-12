package com.gls.job.executor.constants;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Data
@Component
@ConfigurationProperties(prefix = JobExecutorProperties.PREFIX, ignoreInvalidFields = true)
public class JobExecutorProperties {

    public static final String PREFIX = FrameworkConstants.BASE_PROPERTIES_PREFIX + ".job.executor";

    private String adminAddresses;
    private String accessToken;
    private String appname;
    private String address;
    private String ip;
    private int port;
    private String basePath = "/data/gls-job";
    private String logBasePath = basePath.concat("/jobLog");
    private String glueSrcPath = basePath.concat("/glueSource");
    private String callbackLogPath = basePath.concat("/callbackLog");
    private int logRetentionDays;
}
