package com.gls.job.executor.web.rpc;

import com.gls.job.core.api.model.*;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.executor.web.service.LogFileService;
import com.gls.job.executor.web.service.TriggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@DubboService
public class ExecutorRpcService implements ExecutorApi {

    @Resource
    private TriggerService triggerService;

    @Resource
    private LogFileService logFileService;

    @Override
    public Result<String> beat() {
        return Result.SUCCESS;
    }

    @Override
    public Result<String> idleBeat(IdleBeatModel idleBeatModel) {
        boolean flag = triggerService.idleBeat(idleBeatModel.getJobId());
        if (flag) {
            return Result.SUCCESS;
        }
        return new Result<>(Result.FAIL_CODE, "job thread is running or has trigger queue.");
    }

    @Override
    public Result<String> run(TriggerModel triggerModel) {
        try {
            boolean flag = triggerService.run(triggerModel);
            if (flag) {
                return Result.SUCCESS;
            }
        } catch (Exception e) {
            return new Result<>(Result.FAIL_CODE, e.getMessage());
        }
        return new Result<>(Result.FAIL_CODE, "job thread run fail.");
    }

    @Override
    public Result<String> kill(KillModel killModel) {
        boolean flag = triggerService.kill(killModel.getJobId());
        if (flag) {
            return Result.SUCCESS;
        }
        return new Result<>(Result.SUCCESS_CODE, "job thread already killed.");
    }

    @Override
    public Result<LogResultModel> log(LogModel logModel) {
        return new Result<>(logFileService.readLogFile(logModel));
    }

}
