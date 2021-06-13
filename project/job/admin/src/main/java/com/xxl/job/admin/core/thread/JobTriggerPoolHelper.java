package com.xxl.job.admin.core.thread;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.web.service.JobTriggerService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * job trigger thread pool helper
 *
 * @author xuxueli 2018-07-03 21:08:07
 */
@Slf4j
public class JobTriggerPoolHelper {

    // ---------------------- trigger pool ----------------------
    private static final JobTriggerPoolHelper helper = new JobTriggerPoolHelper();
    private final ConcurrentMap<Long, AtomicInteger> jobTimeoutCountMap = new ConcurrentHashMap<>();
    // fast/slow thread pool
    private ThreadPoolExecutor fastTriggerPool = null;
    private ThreadPoolExecutor slowTriggerPool = null;
    // job timeout count
    private volatile long minTim = System.currentTimeMillis() / 60000;     // ms > min

    @Resource
    private JobTriggerService jobTriggerService;
    @Resource
    private JobAdminProperties jobAdminProperties;

    public static void toStart() {
        helper.start();
    }

    // ---------------------- helper ----------------------

    public static void toStop() {
        helper.stop();
    }

    /**
     * @param jobId
     * @param triggerType
     * @param failRetryCount        >=0: use this param
     *                              <0: use param from job info config
     * @param executorShardingParam
     * @param executorParam         null: use job param
     *                              not null: cover job param
     */
    public static void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, String addressList) {
        helper.addTrigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
    }

    public void start() {
        fastTriggerPool = new ThreadPoolExecutor(
                10,
                jobAdminProperties.getTriggerPoolFastMax(),
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "gls-job, admin JobTriggerPoolHelper-fastTriggerPool-" + r.hashCode()));

        slowTriggerPool = new ThreadPoolExecutor(
                10,
                jobAdminProperties.getTriggerPoolSlowMax(),
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000),
                r -> new Thread(r, "gls-job, admin JobTriggerPoolHelper-slowTriggerPool-" + r.hashCode()));
    }

    public void stop() {
        //triggerPool.shutdown();
        fastTriggerPool.shutdownNow();
        slowTriggerPool.shutdownNow();
        log.info(">>>>>>>>> gls-job trigger thread pool shutdown success.");
    }

    /**
     * add trigger
     */
    public void addTrigger(final Long jobId,
                           final TriggerType triggerType,
                           final int failRetryCount,
                           final String executorShardingParam,
                           final String executorParam,
                           final String addressList) {

        // choose thread pool
        ThreadPoolExecutor triggerPool = fastTriggerPool;
        AtomicInteger jobTimeoutCount = jobTimeoutCountMap.get(jobId);
        if (jobTimeoutCount != null && jobTimeoutCount.get() > 10) {      // job-timeout 10 times in 1 min
            triggerPool = slowTriggerPool;
        }

        // trigger
        triggerPool.execute(() -> {

            long start = System.currentTimeMillis();

            try {
                // do trigger
                jobTriggerService.trigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {

                // check timeout-count-map
                long minTimNow = System.currentTimeMillis() / 60000;
                if (minTim != minTimNow) {
                    minTim = minTimNow;
                    jobTimeoutCountMap.clear();
                }

                // incr timeout-count-map
                long cost = System.currentTimeMillis() - start;
                if (cost > 500) {       // ob-timeout threshold 500ms
                    AtomicInteger timeoutCount = jobTimeoutCountMap.putIfAbsent(jobId, new AtomicInteger(1));
                    if (timeoutCount != null) {
                        timeoutCount.incrementAndGet();
                    }
                }

            }

        });
    }

}
