package com.gls.job.dashboard.core.init;

import com.gls.job.dashboard.core.job.BaseJob;
import com.gls.job.dashboard.web.service.QuartzService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author george
 */
@Component
public class JobInitRunner implements CommandLineRunner {

    @Resource
    private QuartzService quartzService;

    @Override
    public void run(String... args) throws Exception {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("name", 1);
        quartzService.deleteJob("job", "test");
        quartzService.addJob(BaseJob.class, "job", "test", "0 * * * * ?", map);

        map.put("name", 2);
        quartzService.deleteJob("job2", "test");
        quartzService.addJob(BaseJob.class, "job2", "test", "10 * * * * ?", map);

        map.put("name", 3);
        quartzService.deleteJob("job3", "test2");
        quartzService.addJob(BaseJob.class, "job3", "test2", "15 * * * * ?", map);
    }
}
