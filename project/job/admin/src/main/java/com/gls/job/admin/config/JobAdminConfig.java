package com.gls.job.admin.config;

import com.gls.job.admin.constants.JobAdminProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author george
 */
@Configuration
@EnableConfigurationProperties(JobAdminProperties.class)
public class JobAdminConfig {
}
