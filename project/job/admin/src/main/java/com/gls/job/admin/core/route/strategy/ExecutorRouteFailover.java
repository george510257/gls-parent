package com.gls.job.admin.core.route.strategy;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
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
public class ExecutorRouteFailover implements ExecutorRouter {

    @Resource
    private ExecutorApiHolder executorApiHolder;

    @Override
    public String route(Long jobId, List<String> addressList) {
        try {
            for (String address : addressList) {
                // beat
                ExecutorApi executorApi = executorApiHolder.load(address);
                Result<String> result = executorApi.beat();
                // beat success
                if (result.getCode() == Result.SUCCESS_CODE) {
                    return address;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;

    }
}
