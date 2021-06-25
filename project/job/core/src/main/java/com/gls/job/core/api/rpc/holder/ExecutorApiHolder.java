package com.gls.job.core.api.rpc.holder;

import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.base.BaseHolder;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ExecutorApiHolder extends BaseHolder<String, ExecutorApi> {

    @Override
    protected void delete(ExecutorApi oldValue, String reason) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
