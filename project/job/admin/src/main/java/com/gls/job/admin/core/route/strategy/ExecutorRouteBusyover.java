package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.IdleBeatModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Slf4j
@Component
public class ExecutorRouteBusyover implements ExecutorRouter {

    @Resource
    private XxlJobScheduler xxlJobScheduler;

    @Override
    public String route(TriggerModel triggerModel, List<String> addressList) {
        try {
            for (String address : addressList) {
                // beat
                ExecutorApi executorApi = xxlJobScheduler.getExecutorBiz(address);
                Result<String> result = executorApi.idleBeat(new IdleBeatModel(triggerModel.getJobId()));
                // beat success
                if (Result.SUCCESS_CODE == result.getCode()) {
                    return address;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
