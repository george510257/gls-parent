package com.gls.job.admin.web.controller;

import com.gls.job.admin.web.service.JobLogGlueService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.exception.JobException;
import org.springframework.util.StringUtils;
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
        try {
            Map<String, Object> map = jobLogGlueService.getIndex(jobId);
            return new Result<>(map);
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("save")
    public Result<String> save(Long jobId, String glueSource, String glueRemark) {
        if (!StringUtils.hasText(glueRemark)) {
            return new Result<>(Result.FAIL_CODE, "请输入源码备注");
        }
        if (glueRemark.length() < 4 || glueRemark.length() > 100) {
            return new Result<>(Result.FAIL_CODE, "源码备注长度限制为4~100");
        }
        try {
            jobLogGlueService.saveGlueSource(jobId, glueSource, glueRemark);
            return Result.SUCCESS;
        } catch (JobException e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }
}
