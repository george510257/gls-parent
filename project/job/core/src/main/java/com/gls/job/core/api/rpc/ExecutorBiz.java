package com.gls.job.core.api.rpc;

import com.gls.job.core.api.model.*;

/**
 * Created by george on 17/3/1.
 */
public interface ExecutorBiz {

    /**
     * beat
     *
     * @return
     */
    public Result<String> beat();

    /**
     * idle beat
     *
     * @param idleBeatModel
     * @return
     */
    public Result<String> idleBeat(IdleBeatModel idleBeatModel);

    /**
     * run
     *
     * @param triggerModel
     * @return
     */
    public Result<String> run(TriggerModel triggerModel);

    /**
     * kill
     *
     * @param killModel
     * @return
     */
    public Result<String> kill(KillModel killModel);

    /**
     * log
     *
     * @param logModel
     * @return
     */
    public Result<LogResultModel> log(LogModel logModel);

}
