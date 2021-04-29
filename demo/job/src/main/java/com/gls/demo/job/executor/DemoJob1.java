package com.gls.demo.job.executor;

import com.gls.job.executor.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 样例任务1
 *
 * @author george
 */
@Slf4j
@Component
public class DemoJob1 implements IJobHandler {

    @Override
    public void execute() throws Exception {
        log.info("DemoJob1 -- execute");
    }

    @Override
    public void init() throws Exception {
        log.info("DemoJob1 -- init");
    }

    @Override
    public void destroy() throws Exception {
        log.info("DemoJob1 -- destroy");
    }
}
