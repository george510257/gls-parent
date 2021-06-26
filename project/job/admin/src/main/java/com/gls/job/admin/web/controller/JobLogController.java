package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class JobLogController {

    @Resource
    private JobLogService jobLogService;

    @GetMapping("/index")
    public Result<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "0") Long jobId) {
        try {
            return new Result<>(jobLogService.getIndexMap(jobId));
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/getJobsByGroup")
    public Result<List<JobInfo>> getJobsByGroup(Long jobGroupId) {
        try {
            return new Result<>(jobLogService.getJobsByGroup(jobGroupId));
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/pageList")
    public Result<Map<String, Object>> pageList(Long jobGroup, Long jobId, int logStatus, String filterTime,
                                                @RequestParam(required = false, defaultValue = "0") int start,
                                                @RequestParam(required = false, defaultValue = "10") int length) {
        try {
            return new Result<>(jobLogService.pageList(jobGroup, jobId, logStatus, filterTime, start, length));
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/logDetail")
    public Result<JobLog> logDetail(Long logId) {
        try {
            return new Result<>(jobLogService.logDetail(logId));
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/logDetailCat")
    public Result<LogResultModel> logDetailCat(String executorAddress, Long triggerTime, Long logId, Integer fromLineNum) {
        try {
            return new Result<>(jobLogService.logDetailCat(executorAddress, triggerTime, logId, fromLineNum));
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/logKill")
    public Result<String> logKill(Long logId) {
        try {
            jobLogService.logKill(logId);
            return Result.SUCCESS;
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/clearLog")
    public Result<String> clearLog(Long groupId, Long jobId, Integer type) {
        try {
            jobLogService.clearLog(groupId, jobId, type);
            return Result.SUCCESS;
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }
}
