package com.gls.job.admin.core.route;

import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
public interface ExecutorRouter {

    /**
     * route address
     *
     * @param triggerParam
     * @param addressList
     * @return
     */
    ReturnT<String> route(TriggerParam triggerParam, List<String> addressList);

}
