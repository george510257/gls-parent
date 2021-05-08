package com.gls.job.admin.web.service;

import com.gls.job.admin.core.server.*;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.api.rpc.client.ExecutorApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author george 2018-10-28 00:18:17
 */
@Slf4j
@Service
public class JobSchedulerService {

    // ---------------------- executor-client ----------------------

    private ConcurrentMap<String, ExecutorApi> executorApiRepository = new ConcurrentHashMap<>();

    @Value("${gls.job.accessToken}")
    private String accessToken;

    public ExecutorApi getExecutorApi(String address) throws Exception {
        // valid
        if (address == null || address.trim().length() == 0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorApi executorApi = executorApiRepository.get(address);
        if (executorApi != null) {
            return executorApi;
        }

        // set-cache
        executorApi = new ExecutorApiClient(address, accessToken);

        executorApiRepository.put(address, executorApi);
        return executorApi;
    }

    // ---------------------- I18n ----------------------

    public void init() throws Exception {
        // init i18n
        initI18n();

        // admin trigger pool start
        JobTriggerPoolHelper.toStart();

        // admin registry monitor run
        JobRegistryServer.getInstance().start();

        // admin fail-monitor run
        JobFailMonitorServer.getInstance().start();

        // admin lose-monitor run ( depend on JobTriggerPoolHelper )
        JobCompleteServer.getInstance().start();

        // admin log report start
        JobLogReportServer.getInstance().start();

        // start-schedule  ( depend on JobTriggerPoolHelper )
        JobScheduleServer.getInstance().start();

        log.info(">>>>>>>>> init gls-job admin success.");
    }

    public void destroy() throws Exception {

        // stop-schedule
        JobScheduleServer.getInstance().toStop();

        // admin log report stop
        JobLogReportServer.getInstance().toStop();

        // admin lose-monitor stop
        JobCompleteServer.getInstance().toStop();

        // admin fail-monitor stop
        JobFailMonitorServer.getInstance().toStop();

        // admin registry stop
        JobRegistryServer.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

    }

    private void initI18n() {
        for (ExecutorBlockStrategy item : ExecutorBlockStrategy.values()) {
//            item.setTitle(I18nUtil.getString("job_conf_block_".concat(item.name())));
        }
    }

}
