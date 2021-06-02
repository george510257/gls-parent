package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouteLast implements ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<>(addressList.get(addressList.size() - 1));
    }

}
