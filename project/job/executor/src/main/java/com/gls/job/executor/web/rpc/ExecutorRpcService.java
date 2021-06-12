package com.gls.job.executor.web.rpc;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.executor.web.service.JobLogService;
import com.gls.job.executor.web.service.TriggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author xuxueli
 * @date 17/3/1
 */
@Slf4j
@DubboService
public class ExecutorRpcService implements ExecutorApi {

    @Resource
    private TriggerService triggerService;

    @Resource
    private JobLogService jobLogService;

    @Override
    public Result<String> beat() {
        return Result.SUCCESS;
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {
        boolean isRunningOrHasQueue = triggerService.idleBeat(idleBeatModel.getJobId());
        if (isRunningOrHasQueue) {
            return new Result<>(Result.FAIL_CODE, "job thread is running or has trigger queue.");
        }
        return Result.SUCCESS;
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        try {
            triggerService.runTrigger(triggerModel);
        } catch (Exception e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return Result.SUCCESS;
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        String message = triggerService.removeJobThread(killModel.getJobId());
        return new Result<>(Result.SUCCESS_CODE, message);
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        LogResultModel logResultModel = jobLogService.readLog(logModel);
        return new Result<>(logResultModel);
    }

}
