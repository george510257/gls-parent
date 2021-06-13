package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.TriggerModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouteRandom implements ExecutorRouter {

    private static final Random LOCAL_RANDOM = new Random();

    @Override
    public String route(TriggerModel triggerModel, List<String> addressList) {
        return addressList.get(LOCAL_RANDOM.nextInt(addressList.size()));
    }

}
