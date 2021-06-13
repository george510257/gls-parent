package com.gls.job.executor;

import com.gls.job.executor.core.constants.JobExecutorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(value = JobExecutorProperties.class)
public class JobExecutorConfig {

}
