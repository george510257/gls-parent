package com.gls.job.core.executor.queue;

import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.common.base.BaseQueueHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author george
 */
public class TriggerQueueHolder extends BaseQueueHolder<TriggerModel> {

    private final Set<Long> logIds = Collections.synchronizedSet(new HashSet<>());

    @Override
    public boolean push(TriggerModel value) {
        Long logId = value.getLogId();
        if (logIds.contains(logId)) {
            return false;
        }
        logIds.add(logId);
        return super.push(value);
    }

    @Override
    public boolean push(List<TriggerModel> list) {
        boolean flag = false;
        for (TriggerModel model : list) {
            flag = push(model);
        }
        return flag;
    }

    @Override
    public TriggerModel pop() {
        TriggerModel model = super.pop();
        if (model != null) {
            logIds.remove(model.getLogId());
        }
        return model;
    }

    @Override
    public List<TriggerModel> pops() {
        List<TriggerModel> list = super.pops();
        for (TriggerModel model : list) {
            logIds.remove(model.getLogId());
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && logIds.isEmpty();
    }

    public boolean contains(Long logId) {
        return logIds.contains(logId);
    }
}
