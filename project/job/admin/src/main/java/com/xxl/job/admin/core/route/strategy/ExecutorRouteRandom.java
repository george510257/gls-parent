package com.xxl.job.admin.core.route.strategy;

import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.xxl.job.admin.core.route.ExecutorRouter;

import java.util.List;
import java.util.Random;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteRandom extends ExecutorRouter {

    private static Random localRandom = new Random();

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        String address = addressList.get(localRandom.nextInt(addressList.size()));
        return new Result<String>(address);
    }

}
