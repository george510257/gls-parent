package com.gls.job.admin.core.scheduler;

import com.gls.job.admin.core.conf.XxlJobAdminConfig;
import com.gls.job.admin.core.server.*;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.api.rpc.client.ExecutorApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author george 2018-10-28 00:18:17
 */

public class XxlJobScheduler {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobScheduler.class);
    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorApi> executorApiRepository = new ConcurrentHashMap<String, ExecutorApi>();

    public static ExecutorApi getExecutorApi(String address) throws Exception {
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
        executorApi = new ExecutorApiClient(address, XxlJobAdminConfig.getAdminConfig().getAccessToken());

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

        logger.info(">>>>>>>>> init gls-job admin success.");
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
//            item.setTitle(I18nUtil.getString("jobconf_block_".concat(item.name())));
        }
    }

}
