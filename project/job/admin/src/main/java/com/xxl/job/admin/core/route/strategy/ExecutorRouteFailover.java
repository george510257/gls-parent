package com.xxl.job.admin.core.route.strategy;

import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.xxl.job.admin.core.route.ExecutorRouter;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import com.xxl.job.admin.core.util.I18nUtil;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteFailover extends ExecutorRouter {

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {

        StringBuffer beatResultSB = new StringBuffer();
        for (String address : addressList) {
            // beat
            Result<String> beatResult = null;
            try {
                ExecutorApi executorApi = XxlJobScheduler.getExecutorBiz(address);
                beatResult = executorApi.beat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                beatResult = new Result<String>(Result.FAIL_CODE, "" + e);
            }
            beatResultSB.append((beatResultSB.length() > 0) ? "<br><br>" : "")
                    .append(I18nUtil.getString("job_conf_beat") + "：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(beatResult.getCode())
                    .append("<br>msg：").append(beatResult.getMsg());

            // beat success
            if (beatResult.getCode() == Result.SUCCESS_CODE) {

                beatResult.setMsg(beatResultSB.toString());
                beatResult.setContent(address);
                return beatResult;
            }
        }
        return new Result<String>(Result.FAIL_CODE, beatResultSB.toString());

    }
}
