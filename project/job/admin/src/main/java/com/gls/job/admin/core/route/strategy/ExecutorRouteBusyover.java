package com.gls.job.admin.core.route.strategy;

import com.gls.framework.api.result.Result;
import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.IdleBeatModel;
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
public class ExecutorRouteBusyover implements ExecutorRouter {
    @Resource
    private ExecutorApiHolder executorApiHolder;

    @Override
    public String route(Long jobId, List<String> addressList) {
        try {
            for (String address : addressList) {
                // beat
                ExecutorApi executorApi = executorApiHolder.load(address);
                Result<String> result = executorApi.idleBeat(new IdleBeatModel(jobId));
                // beat success
                if (Result.SUCCESS_CODE.equals(result.getCode())) {
                    return address;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
