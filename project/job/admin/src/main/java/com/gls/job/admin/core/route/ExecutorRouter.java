package com.gls.job.admin.core.route;

import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by george on 17/3/10.
 */
public abstract class ExecutorRouter {
    protected static Logger logger = LoggerFactory.getLogger(ExecutorRouter.class);

    /**
     * route address
     *
     * @param addressList
     * @return ReturnT.content=address
     */
    public abstract Result<String> route(TriggerModel triggerModel, List<String> addressList);

}
