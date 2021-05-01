package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;

import java.util.List;
import java.util.Random;

/**
 * @author george
 * @date 17/3/10
 */
public class ExecutorRouteRandom implements ExecutorRouter {

    private static final Random LOCAL_RANDOM = new Random();

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        String address = addressList.get(LOCAL_RANDOM.nextInt(addressList.size()));
        return new Result<>(address);
    }

}
