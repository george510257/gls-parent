package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.ExecutorApi;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 * @date 17/3/10
 */
@Slf4j
public class ExecutorRouteFailover implements ExecutorRouter {

    @Resource
    private JobSchedulerService jobSchedulerService;

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {

        StringBuilder beatResultSB = new StringBuilder();
        for (String address : addressList) {
            // beat
            Result<String> beatResult;
            try {
                ExecutorApi executorApi = jobSchedulerService.getExecutorApi(address);
                beatResult = executorApi.beat();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                beatResult = new Result<>(Result.FAIL_CODE, "" + e);
            }
            beatResultSB
                    .append((beatResultSB.length() > 0) ? "<br><br>" : "")
                    .append(I18nUtil.getString("job_conf_beat"))
                    .append("：")
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
        return new Result<>(Result.FAIL_CODE, beatResultSB.toString());

    }
}
