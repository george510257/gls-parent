package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.core.api.model.IdleBeatModel;
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
public class ExecutorRouteBusyover implements ExecutorRouter {

    @Resource
    private JobSchedulerService jobSchedulerService;

    @Override
    public Result<String> route(TriggerModel triggerModel, List<String> addressList) {
        StringBuilder idleBeatResultSB = new StringBuilder();
        for (String address : addressList) {
            // beat
            Result<String> idleBeatResult;
            try {
                ExecutorApi executorApi = jobSchedulerService.getExecutorApi(address);
                idleBeatResult = executorApi.idleBeat(new IdleBeatModel(triggerModel.getJobId()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                idleBeatResult = new Result<>(Result.FAIL_CODE, "" + e);
            }
            idleBeatResultSB.append((idleBeatResultSB.length() > 0) ? "<br><br>" : "").append(I18nUtil.getString("job_conf_idleBeat")).append("：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(idleBeatResult.getCode())
                    .append("<br>msg：").append(idleBeatResult.getMsg());

            // beat success
            if (idleBeatResult.getCode() == Result.SUCCESS_CODE) {
                idleBeatResult.setMsg(idleBeatResultSB.toString());
                idleBeatResult.setContent(address);
                return idleBeatResult;
            }
        }

        return new Result<>(Result.FAIL_CODE, idleBeatResultSB.toString());
    }

}
