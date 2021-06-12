package com.gls.job.executor;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.common.daemon.DaemonHolder;
import com.gls.job.executor.constants.JobExecutorProperties;
import com.gls.job.executor.handler.JobHandler;
import com.gls.job.executor.handler.JobHandlerHolder;
import com.gls.job.executor.handler.builder.GlueBuilder;
import com.gls.job.executor.handler.builder.SpringGlueBuilder;
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
@EnableConfigurationProperties(value = JobExecutorProperties.class)
public class JobExecutorConfig {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private Map<String, JobHandler> jobHandlers;

    @Resource
    private Map<String, BaseThread> daemonThreads;

    @Bean
    public GlueBuilder glueBuilder() {
        return new SpringGlueBuilder(applicationContext);
    }

    @Bean
    public JobHandlerHolder jobHandlerHolder() {
        JobHandlerHolder jobHandlerHolder = new JobHandlerHolder(applicationContext);
        jobHandlerHolder.init();
        jobHandlerHolder.regist(jobHandlers);
        return jobHandlerHolder;
    }

    @Bean
    public DaemonHolder daemonHolder() {
        DaemonHolder daemonHolder = new DaemonHolder();
        daemonHolder.registByThread(daemonThreads);
        return daemonHolder;
    }
}
