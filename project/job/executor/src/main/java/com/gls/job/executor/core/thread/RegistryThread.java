package com.gls.job.executor.core.thread;

import com.gls.job.core.base.BaseThread;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.executor.web.service.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class RegistryThread extends BaseThread {
    @Resource
    private RegistryService registryService;

    @Override
    protected void destroyExecute() throws Exception {
        registryService.registryRemove();
        log.info(">>>>>>>>>>> gls-job, executor RegistryThread thread destroy.");
    }

    @Override
    protected void doExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor RegistryThread thread doExecute.");
        registryService.registry();
    }

    @Override
    protected void initExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor RegistryThread thread init.");
    }

    @Override
    protected void sleepExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, executor RegistryThread thread sleep.");
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }
}
