package com.gls.demo.job.executor;

import com.gls.job.executor.handler.annotation.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 样例任务2
 *
 * @author george
 */
@Slf4j
@Component
public class DemoJob2 {

    @Job(value = "demoJob2", init = "init", destroy = "destroy")
    public void execute() throws Exception {
        log.info("DemoJob2 -- execute");
    }

    public void init() throws Exception {
        log.info("DemoJob2 -- init");
    }

    public void destroy() throws Exception {
        log.info("DemoJob2 -- destroy");
    }
}
