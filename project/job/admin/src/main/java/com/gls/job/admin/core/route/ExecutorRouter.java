package com.gls.job.admin.core.route;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
public interface ExecutorRouter {

    /**
     * route address
     *
     * @param jobId
     * @param addressList
     * @return
     */
    String route(Long jobId, List<String> addressList);

}
