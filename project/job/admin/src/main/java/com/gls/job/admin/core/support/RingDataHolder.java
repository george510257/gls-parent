package com.gls.job.admin.core.support;

import com.gls.job.core.base.BaseHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author george
 */
@Component
public class RingDataHolder extends BaseHolder<Integer, List<Long>> {

    @Override
    protected void delete(List<Long> oldValue, String reason) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
