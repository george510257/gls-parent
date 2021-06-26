package com.gls.job.admin.web.controller;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.web.service.JobIndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author george
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private JobIndexService jobIndexService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboardInfo() {
        Map<String, Object> dashboardInfo = jobIndexService.getDashboardInfo();
        return new Result<>(dashboardInfo);
    }

    @GetMapping("/chart")
    public Result<Map<String, Object>> getChartInfo(Date startDate, Date endDate) {
        Map<String, Object> chartInfo = jobIndexService.getChartInfo(startDate, endDate);
        return new Result<>(chartInfo);
    }

}
