package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.model.JobLogGlue;
import com.gls.job.admin.web.service.JobLogGlueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@RestController
@RequestMapping("/code")
public class JobCodeController {
    @Resource
    private JobLogGlueService jobLogGlueService;

    @GetMapping("/index")
    public Result<Map<String, Object>> index(Long jobId) {
        Map<String, Object> map = jobLogGlueService.getIndex(jobId);
        return new Result<>(map);
    }

    @PostMapping("add")
    public Result<String> save(JobLogGlue jobLogGlue) {
        jobLogGlueService.save(jobLogGlue);
        return Result.SUCCESS;
    }
}
