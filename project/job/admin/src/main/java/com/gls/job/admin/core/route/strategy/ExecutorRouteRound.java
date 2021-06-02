package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Component
public class ExecutorRouteRound implements ExecutorRouter {

    private static final ConcurrentMap<Long, AtomicInteger> ROUTE_COUNT_EACH_JOB = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    private static int count(Long jobId) {
        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            ROUTE_COUNT_EACH_JOB.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }

        AtomicInteger count = ROUTE_COUNT_EACH_JOB.get(jobId);
        if (count == null || count.get() > 1000000) {
            // 初始化时主动Random一次，缓解首次压力
            count = new AtomicInteger(new Random().nextInt(100));
        } else {
            // count++
            count.addAndGet(1);
        }
        ROUTE_COUNT_EACH_JOB.put(jobId, count);
        return count.get();
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        String address = addressList.get(count(triggerParam.getJobId()) % addressList.size());
        return new ReturnT<>(address);
    }

}
