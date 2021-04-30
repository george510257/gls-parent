package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 单个JOB对应的每个执行器，最久为使用的优先被选举
 * a、LFU(Least Frequently Used)：最不经常使用，频率/次数
 * b(*)、LRU(Least Recently Used)：最近最久未使用，时间
 * <p>
 * Created by george on 17/3/10.
 */
public class ExecutorRouteLRU extends ExecutorRouter {

    private static final ConcurrentMap<Long, LinkedHashMap<String, String>> JOB_LRU_MAP = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    public String route(Long jobId, List<String> addressList) {

        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            JOB_LRU_MAP.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }

        // init lru
        LinkedHashMap<String, String> lruItem = JOB_LRU_MAP.get(jobId);
        if (lruItem == null) {
            /**
             * LinkedHashMap
             *      a、accessOrder：true=访问顺序排序（get/put时排序）；false=插入顺序排期；
             *      b、removeEldestEntry：新增元素时将会调用，返回true时会删除最老元素；可封装LinkedHashMap并重写该方法，比如定义最大容量，超出是返回true即可实现固定长度的LRU算法；
             */
            lruItem = new LinkedHashMap<>(16, 0.75f, true);
            JOB_LRU_MAP.putIfAbsent(jobId, lruItem);
        }

        // put new
        for (String address : addressList) {
            if (!lruItem.containsKey(address)) {
                lruItem.put(address, address);
            }
        }
        // remove old
        List<String> delKeys = new ArrayList<>();
        for (String existKey : lruItem.keySet()) {
            if (!addressList.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (delKeys.size() > 0) {
            for (String delKey : delKeys) {
                lruItem.remove(delKey);
            }
        }

        // load
        String eldestKey = lruItem.entrySet().iterator().next().getKey();
        return lruItem.get(eldestKey);
    }

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        String address = route(triggerModel.getJobId(), addressList);
        return new Result<>(address);
    }

}
