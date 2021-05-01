package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;

import java.util.List;

/**
 * @author george
 * @date 17/3/10
 */
public class ExecutorRouteLast implements ExecutorRouter {

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        return new Result<>(addressList.get(addressList.size() - 1));
    }

}
