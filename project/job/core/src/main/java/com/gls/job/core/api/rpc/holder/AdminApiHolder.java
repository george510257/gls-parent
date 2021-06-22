package com.gls.job.core.api.rpc.holder;

import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.base.BaseHolder;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class AdminApiHolder extends BaseHolder<String, AdminApi> {

    @Override
    protected void delete(AdminApi oldValue, String reason) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
