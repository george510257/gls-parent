package com.gls.job.admin.core.holder;

import com.gls.job.core.base.holder.BaseHolder;

import java.util.List;

/**
 * @author george
 */
public class RingDataHolder extends BaseHolder<Integer, List<Long>> {

    private static final RingDataHolder INSTANCE = new RingDataHolder();

    private RingDataHolder() {
    }

    public static RingDataHolder getInstance() {
        return INSTANCE;
    }
}
