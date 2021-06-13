package com.gls.job.admin.core.enums;

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
    FIRST("first", "executorRouteFirst"),
    LAST("last", "executorRouteLast"),
    ROUND("round", "executorRouteRound"),
    RANDOM("random", "executorRouteRandom"),
    CONSISTENT_HASH("consistenthash", "executorRouteConsistentHash"),
    LEAST_FREQUENTLY_USED("lfu", "executorRouteLFU"),
    LEAST_RECENTLY_USED("lru", "executorRouteLRU"),
    FAILOVER("failover", "executorRouteFailover"),
    BUSYOVER("busyover", "executorRouteBusyover"),
    SHARDING_BROADCAST("shard", "");

    private final String title;
    private final String routerKey;

}
