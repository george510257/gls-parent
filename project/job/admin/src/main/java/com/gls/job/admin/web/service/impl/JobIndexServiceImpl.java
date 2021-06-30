package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobLogReportEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogReportRepository;
import com.gls.job.admin.web.service.JobIndexService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author george
 */
@Service("jobIndexService")
public class JobIndexServiceImpl implements JobIndexService {
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogReportRepository jobLogReportRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;

    @Override
    public Map<String, Object> getChartInfo(Date startDate, Date endDate) {
        // process
        List<String> triggerDayList = new ArrayList<>();
        List<Long> triggerDayCountRunningList = new ArrayList<>();
        List<Long> triggerDayCountSucList = new ArrayList<>();
        List<Long> triggerDayCountFailList = new ArrayList<>();
        AtomicLong triggerCountRunningTotal = new AtomicLong();
        AtomicLong triggerCountSucTotal = new AtomicLong();
        AtomicLong triggerCountFailTotal = new AtomicLong();
        List<JobLogReportEntity> jobLogReportEntities = jobLogReportRepository.getByTriggerDayBetween(startDate, endDate);
        if (ObjectUtils.isEmpty(jobLogReportEntities)) {
            for (int i = -6; i <= 0; i++) {
                triggerDayList.add(DateUtil.formatDate(DateUtil.offsetDay(new Date(), i)));
                triggerDayCountRunningList.add(0L);
                triggerDayCountSucList.add(0L);
                triggerDayCountFailList.add(0L);
            }
        } else {
            for (JobLogReportEntity jobLogReportEntity : jobLogReportEntities) {
                String day = DateUtil.formatDate(jobLogReportEntity.getTriggerDay());
                long triggerDayCountRunning = jobLogReportEntity.getRunningCount();
                long triggerDayCountSuc = jobLogReportEntity.getSucCount();
                long triggerDayCountFail = jobLogReportEntity.getFailCount();
                triggerDayList.add(day);
                triggerDayCountRunningList.add(triggerDayCountRunning);
                triggerDayCountSucList.add(triggerDayCountSuc);
                triggerDayCountFailList.add(triggerDayCountFail);
                triggerCountRunningTotal.addAndGet(triggerDayCountRunning);
                triggerCountSucTotal.addAndGet(triggerDayCountSuc);
                triggerCountFailTotal.addAndGet(triggerDayCountFail);
            }
        }
        Map<String, Object> result = new HashMap<>(7);
        result.put("triggerDayList", triggerDayList);
        result.put("triggerDayCountRunningList", triggerDayCountRunningList);
        result.put("triggerDayCountSucList", triggerDayCountSucList);
        result.put("triggerDayCountFailList", triggerDayCountFailList);
        result.put("triggerCountRunningTotal", triggerCountRunningTotal.get());
        result.put("triggerCountSucTotal", triggerCountSucTotal.get());
        result.put("triggerCountFailTotal", triggerCountFailTotal.get());
        return result;
    }

    @Override
    public Map<String, Object> getDashboardInfo() {
        Map<String, Object> dashboardInfo = new HashMap<>(4);
        // jobInfoCount
        long jobInfoCount = jobInfoRepository.count();
        dashboardInfo.put("jobInfoCount", jobInfoCount);
        Map<String, Long> reportTotal = jobLogReportRepository.getReportTotal();
        // jobLogCount
        long jobLogCount = reportTotal.get("runningCount") + reportTotal.get("sucCount") + reportTotal.get("failCount");
        dashboardInfo.put("jobLogCount", jobLogCount);
        // jobLogSuccessCount
        long jobLogSuccessCount = reportTotal.get("sucCount");
        dashboardInfo.put("jobLogSuccessCount", jobLogSuccessCount);
        // executorCount
        long executorCount = getExecutorCount();
        dashboardInfo.put("executorCount", executorCount);
        return dashboardInfo;
    }

    private long getExecutorCount() {
        Set<String> addressSet = new HashSet<>();
        for (JobGroupEntity jobGroupEntity : jobGroupRepository.findAll()) {
            List<String> addressList = jobGroupEntity.getAddressList();
            addressSet.addAll(addressList);
        }
        return addressSet.size();
    }
}
