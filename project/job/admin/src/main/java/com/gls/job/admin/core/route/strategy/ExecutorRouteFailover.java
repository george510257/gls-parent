package com.gls.job.admin.core.route.strategy;

import com.gls.job.admin.core.route.ExecutorRouter;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.core.biz.ExecutorBiz;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuxueli
 * @date 17/3/10
 */
@Slf4j
@Component
public class ExecutorRouteFailover implements ExecutorRouter {

    @Resource
    private JobSchedulerService jobSchedulerService;

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String address : addressList) {
            // beat
            ReturnT<String> beatResult;
            try {
                ExecutorBiz executorBiz = jobSchedulerService.getExecutorBiz(address);
                beatResult = executorBiz.beat();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                beatResult = new ReturnT<>(ReturnT.FAIL_CODE, "" + e);
            }
            stringBuilder
                    .append((stringBuilder.length() > 0) ? "<br><br>" : "")
                    .append(I18nUtil.getString("job_conf_beat"))
                    .append("：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(beatResult.getCode())
                    .append("<br>msg：").append(beatResult.getMsg());

            // beat success
            if (beatResult.getCode() == ReturnT.SUCCESS_CODE) {

                beatResult.setMsg(stringBuilder.toString());
                beatResult.setContent(address);
                return beatResult;
            }
        }
        return new ReturnT<>(ReturnT.FAIL_CODE, stringBuilder.toString());

    }
}
