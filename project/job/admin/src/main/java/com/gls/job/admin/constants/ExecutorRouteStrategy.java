package com.gls.job.admin.constants;

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
    FIRST("第一个", "executorRouteFirst"),
    LAST("最后一个", "executorRouteLast"),
    ROUND("轮询", "executorRouteRound"),
    RANDOM("随机", "executorRouteRandom"),
    CONSISTENT_HASH("一致性HASH", "executorRouteConsistentHash"),
    LEAST_FREQUENTLY_USED("最不经常使用", "executorRouteLFU"),
    LEAST_RECENTLY_USED("最近最久未使用", "executorRouteLRU"),
    FAILOVER("故障转移", "executorRouteFailover"),
    BUSYOVER("忙碌转移", "executorRouteBusyover"),
    SHARDING_BROADCAST("分片广播", "");

    private final String title;
    private final String routerKey;

}
