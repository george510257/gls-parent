package com.gls.framework.core;

import com.gls.framework.core.result.ResultProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author george
 */
@Configuration
@ComponentScan
@PropertySource(value = "classpath:application-framework.properties")
@EnableConfigurationProperties(ResultProperties.class)
@EnableDiscoveryClient
public class FrameworkCoreConfig {
}
