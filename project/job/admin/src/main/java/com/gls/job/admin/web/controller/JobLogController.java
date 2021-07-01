package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.model.query.QueryJobLog;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private JobInfoService jobInfoService;
    @Resource
    private JobLogService jobLogService;

    @GetMapping("/index")
    public Result<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "0") Long jobId) {
        return new Result<>(jobLogService.getIndexMap(jobId));
    }

    @PostMapping("/pageList")
    public Result<Page<JobLog>> pageList(QueryJobLog queryJobLog, Pageable pageable) {
        Page<JobLog> page = jobLogService.getPage(queryJobLog, pageable);
        return new Result<>(page);
    }

    @GetMapping("/getByJobGroupId")
    public Result<List<JobInfo>> getByJobGroupId(Long jobGroupId) {
        return new Result<>(jobInfoService.getByJobGroupId(jobGroupId));
    }

    @GetMapping("/getById")
    public Result<JobLog> getById(Long logId) {
        return new Result<>(jobLogService.getById(logId));
    }

    @PostMapping("/clearLog")
    public Result<String> clearLog(Long groupId, Long jobId, Integer type) {
        jobLogService.clearLog(groupId, jobId, type);
        return Result.SUCCESS;
    }

    @PostMapping("/logDetailCat")
    public Result<LogResultModel> logDetailCat(LogModel logModel) {
        return new Result<>(jobLogService.logDetailCat(logModel));
    }

    @PostMapping("/logKill")
    public Result<String> logKill(Long logId) {
        jobLogService.logKill(logId);
        return Result.SUCCESS;
    }
}
