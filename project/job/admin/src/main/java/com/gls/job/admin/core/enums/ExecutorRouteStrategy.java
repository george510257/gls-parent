package com.gls.job.admin.core.enums;

import com.gls.job.admin.core.route.strategy.*;
import com.gls.job.admin.core.util.I18nUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Getter
@AllArgsConstructor
public enum ExecutorRouteStrategy {

    /**
     *
     */
    FIRST(I18nUtil.getString("job_conf_route_first"), ExecutorRouteFirst.class.getName()),
    LAST(I18nUtil.getString("job_conf_route_last"), ExecutorRouteLast.class.getName()),
    ROUND(I18nUtil.getString("job_conf_route_round"), ExecutorRouteRound.class.getName()),
    RANDOM(I18nUtil.getString("job_conf_route_random"), ExecutorRouteRandom.class.getName()),
    CONSISTENT_HASH(I18nUtil.getString("job_conf_route_consistenthash"), ExecutorRouteConsistentHash.class.getName()),
    LEAST_FREQUENTLY_USED(I18nUtil.getString("job_conf_route_lfu"), ExecutorRouteLFU.class.getName()),
    LEAST_RECENTLY_USED(I18nUtil.getString("job_conf_route_lru"), ExecutorRouteLRU.class.getName()),
    FAILOVER(I18nUtil.getString("job_conf_route_failover"), ExecutorRouteFailover.class.getName()),
    BUSYOVER(I18nUtil.getString("job_conf_route_busyover"), ExecutorRouteBusyover.class.getName()),
    SHARDING_BROADCAST(I18nUtil.getString("job_conf_route_shard"), null);

    private final String title;
    private final String routerName;

}
