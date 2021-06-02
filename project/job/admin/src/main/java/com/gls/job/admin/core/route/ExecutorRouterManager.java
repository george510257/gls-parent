package com.gls.job.admin.core.route;

import com.gls.job.admin.core.enums.ExecutorRouteStrategy;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouterManager {

    @Resource
    private Map<String, ExecutorRouter> executorRouterMap;

    /**
     * route address
     *
     * @param triggerParam
     * @param addressList
     * @return
     */
    ReturnT<String> route(ExecutorRouteStrategy executorRouteStrategy, TriggerParam triggerParam, List<String> addressList) {
        return executorRouterMap.get(executorRouteStrategy.getRouterName()).route(triggerParam, addressList);
    }

}
