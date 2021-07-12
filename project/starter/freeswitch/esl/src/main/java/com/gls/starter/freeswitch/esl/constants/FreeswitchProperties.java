package com.gls.starter.freeswitch.esl.constants;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Data
@Component
@ConfigurationProperties(prefix = FreeswitchProperties.PREFIX, ignoreInvalidFields = true)
public class FreeswitchProperties {
    public static final String PREFIX = FrameworkConstants.BASE_PROPERTIES_PREFIX + ".freeswitch";
    private String host = "localhost";
    private int port = 8021;
    private String password = "ClueCon";
    private int timeoutSeconds = 20;
}
