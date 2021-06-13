package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.TriggerModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouteLast implements ExecutorRouter {

    @Override
    public String route(TriggerModel triggerModel, List<String> addressList) {
        return addressList.get(addressList.size() - 1);
    }

}
