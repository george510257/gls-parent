package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouteFirst implements ExecutorRouter {

    @Override
    public String route(Long jobId, List<String> addressList) {
        return addressList.get(0);
    }

}
