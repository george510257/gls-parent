package com.gls.job.executor;

import com.gls.job.executor.core.constants.ExecutorProperties;
import com.gls.job.executor.core.handler.IJobHandler;
import com.gls.job.executor.web.repository.JobHandlerRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(value = ExecutorProperties.class)
public class JobExecutorConfig {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private Map<String, IJobHandler> jobHandlers;

    @Bean
    public JobHandlerRepository jobHandlerRepository() {
        JobHandlerRepository jobHandlerRepository = new JobHandlerRepository();
        jobHandlerRepository.init(applicationContext);
        jobHandlerRepository.registJobHandler(jobHandlers);
        return jobHandlerRepository;
    }
}
