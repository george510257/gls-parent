package com.gls.job.core.executor.thread;

import com.gls.job.core.common.base.BaseThread;
import com.gls.job.core.common.constants.JobConstants;
import com.gls.job.core.executor.web.service.RegistryService;
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
    protected void initExecute() throws Exception {

    }

    @Override
    protected void doExecute() throws Exception {
        registryService.registry();
    }

    @Override
    protected void sleepExecute() throws Exception {
        TimeUnit.SECONDS.sleep(JobConstants.BEAT_TIMEOUT);
    }

    @Override
    protected void destroyExecute() throws Exception {
        registryService.registryRemove();
    }
}
