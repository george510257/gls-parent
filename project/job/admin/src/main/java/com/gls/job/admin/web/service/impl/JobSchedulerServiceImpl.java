package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.api.rpc.client.ExecutorApiClient;
import com.xxl.job.admin.core.thread.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class JobSchedulerServiceImpl implements JobSchedulerService {
    @Resource
    public I18nHelper i18nHelper;
    @Resource
    private JobAdminProperties jobAdminProperties;
    // ---------------------- executor-client ----------------------
    private ConcurrentMap<String, ExecutorApi> executorBizRepository = new ConcurrentHashMap<String, ExecutorApi>();

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

    // ---------------------- I18n ----------------------

    public void init() throws Exception {
        // init i18n
        initI18n();

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

        // admin registry monitor run
        JobRegistryHelper.getInstance().start();

        // admin fail-monitor run
        JobFailMonitorHelper.getInstance().start();

        // admin lose-monitor run ( depend on JobTriggerPoolHelper )
        JobCompleteHelper.getInstance().start();

        // admin log report start
        JobLogReportHelper.getInstance().start();

        // start-schedule  ( depend on JobTriggerPoolHelper )
        JobScheduleHelper.getInstance().start();

        log.info(">>>>>>>>> init gls-job admin success.");
    }

    public void destroy() throws Exception {

        // stop-schedule
        JobScheduleHelper.getInstance().toStop();

        // admin log report stop
        JobLogReportHelper.getInstance().toStop();

        // admin lose-monitor stop
        JobCompleteHelper.getInstance().toStop();

        // admin fail-monitor stop
        JobFailMonitorHelper.getInstance().toStop();

        // admin registry stop
        JobRegistryHelper.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

    }

    private void initI18n() {
        for (ExecutorBlockStrategy item : ExecutorBlockStrategy.values()) {
            item.setTitle(i18nHelper.getString("job_conf_block_".concat(item.name())));
        }
    }
}
