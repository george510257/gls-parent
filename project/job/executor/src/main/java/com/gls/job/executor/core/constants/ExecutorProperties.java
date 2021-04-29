package com.gls.job.executor.core.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author george
 */
@Data
@ConfigurationProperties(prefix = "gls.job.executor")
public class ExecutorProperties {

    private Long logRetentionDays;
}
