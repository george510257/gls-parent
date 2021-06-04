package com.xxl.job.admin.core.route.strategy;

import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.xxl.job.admin.core.route.ExecutorRouter;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        return new Result<String>(addressList.get(addressList.size() - 1));
    }

}
