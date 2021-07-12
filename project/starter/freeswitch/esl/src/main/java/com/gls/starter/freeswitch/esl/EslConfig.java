package com.gls.starter.freeswitch.esl;

import com.gls.starter.freeswitch.esl.constants.FreeswitchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(FreeswitchProperties.class)
public class EslConfig {
}
