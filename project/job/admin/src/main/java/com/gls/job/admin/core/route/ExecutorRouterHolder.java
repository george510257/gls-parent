package com.gls.job.admin.core.route;

import com.gls.job.core.base.BaseHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Component
public class ExecutorRouterHolder extends BaseHolder<String, ExecutorRouter> {

    @Resource
    private Map<String, ExecutorRouter> executorRouterMap;

    @Override
    protected void delete(ExecutorRouter oldValue, String reason) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        regist(executorRouterMap);
    }
}
