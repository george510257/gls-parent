package com.gls.job.admin.web.controller;

import cn.hutool.core.date.DateUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.core.util.LoginUserUtil;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.model.query.QueryJobLog;
import com.gls.job.admin.web.service.JobLogService;
import com.gls.job.core.api.model.LogResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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

    @PostMapping("/clearLog")
    public Result<String> clearLog(Long groupId, Long jobId, Integer type) {
        try {
            jobLogService.clearLog(groupId, jobId, type);
            return Result.SUCCESS;
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/getJobsByGroup")
    public Result<List<JobInfo>> getJobsByGroup(Long jobGroupId) {
        try {
            return new Result<>(jobLogService.getJobsByGroup(jobGroupId));
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/index")
    public Result<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "0") Long jobId) {
        try {
            return new Result<>(jobLogService.getIndexMap(jobId));
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @GetMapping("/logDetail")
    public Result<JobLog> logDetail(Long logId) {
        try {
            return new Result<>(jobLogService.logDetail(logId));
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/logDetailCat")
    public Result<LogResultModel> logDetailCat(String executorAddress, Long triggerTime, Long logId, Integer fromLineNum) {
        try {
            return new Result<>(jobLogService.logDetailCat(executorAddress, triggerTime, logId, fromLineNum));
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/logKill")
    public Result<String> logKill(Long logId) {
        try {
            jobLogService.logKill(logId);
            return Result.SUCCESS;
        } catch (GlsException e) {
            log.error(e.getMessage(), e);
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @PostMapping("/pageList")
    public Result<Map<String, Object>> pageList(Long jobGroup, Long jobId, int logStatus, String filterTime,
                                                @RequestParam(required = false, defaultValue = "0") int start,
                                                @RequestParam(required = false, defaultValue = "10") int length) {
        if (!LoginUserUtil.validPermission(jobGroup)) {
            new Result<>(Result.FAIL_CODE, "权限拦截");
        }
        // parse param
        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (filterTime != null && filterTime.trim().length() > 0) {
            String[] temp = filterTime.split(" - ");
            if (temp.length == 2) {
                triggerTimeStart = DateUtil.parseDateTime(temp[0]);
                triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
            }
        }
        Page<JobLog> page = jobLogService.getPage(
                new QueryJobLog(jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus),
                PageRequest.of(start, length, Sort.by(Sort.Direction.DESC, "triggerTime")));
        // package result
        Map<String, Object> maps = new HashMap<>();
        // 总记录数
        maps.put("recordsTotal", page.getTotalElements());
        // 过滤后的总记录数
        maps.put("recordsFiltered", page.getTotalElements());
        // 分页列表
        maps.put("data", page.getContent());
        return new Result<>(maps);
    }
}
