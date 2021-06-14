package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.cron.CronExpression;
import com.gls.job.admin.core.enums.ScheduleType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.api.rpc.client.ExecutorApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author georg
 */
@Slf4j
@Service
public class JobSchedulerServiceImpl implements JobSchedulerService {

    private final ConcurrentMap<String, ExecutorApi> executorBizRepository = new ConcurrentHashMap<>();
    @Resource
    public I18nHelper i18nHelper;

    // ---------------------- executor-client ----------------------
    @Resource
    private JobAdminProperties jobAdminProperties;

    @Override
    public ExecutorApi getExecutorBiz(String address) {
        // valid
        if (address == null || address.trim().length() == 0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorApi executorApi = executorBizRepository.get(address);
        if (executorApi != null) {
            return executorApi;
        }

        // set-cache
        executorApi = new ExecutorApiClient(address, jobAdminProperties.getAccessToken());

        executorBizRepository.put(address, executorApi);
        return executorApi;
    }

    @Override
    public Date generateNextValidTime(JobInfo jobInfo, Date lastTime) throws ParseException {
        ScheduleType scheduleType = jobInfo.getScheduleType();
        if (ScheduleType.CRON == scheduleType) {
            return new CronExpression(jobInfo.getScheduleConf()).getNextValidTimeAfter(lastTime);
        } else if (ScheduleType.FIX_RATE == scheduleType) {
            return new Date(lastTime.getTime() + Integer.parseInt(jobInfo.getScheduleConf()) * 1000L);
        }
        return null;
    }

}
