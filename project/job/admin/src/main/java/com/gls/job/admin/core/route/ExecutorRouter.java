package com.gls.job.admin.core.route;

import com.gls.job.core.api.model.TriggerModel;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
public interface ExecutorRouter {

    /**
     * route address
     *
     * @param triggerModel
     * @param addressList
     * @return
     */
    String route(TriggerModel triggerModel, List<String> addressList);

}
