package com.gls.job.executor.core.thread;

import com.gls.job.core.base.thread.BaseThread;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.executor.web.service.RegistryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Component
public class RegistryThread extends BaseThread {

    @Resource
    private RegistryService registryService;

    @Override
    protected void initExecute() {
    }

    @Override
    protected void doExecute() {
        registryService.registry();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() {
        registryService.registryRemove();
    }
}
