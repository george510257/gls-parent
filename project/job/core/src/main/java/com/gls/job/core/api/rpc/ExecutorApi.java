package com.gls.job.core.api.rpc;

import com.gls.job.core.api.model.*;

/**
 * @author george
 */
public interface ExecutorApi {

    /**
     * beat
     *
     * @return
     */
    Result<String> beat();

    /**
     * idle beat
     *
     * @param idleBeatModel
     * @return
     */
    Result<String> idleBeat(IdleBeatModel idleBeatModel);

    /**
     * run
     *
     * @param triggerModel
     * @return
     */
    Result<String> run(TriggerModel triggerModel);

    /**
     * kill
     *
     * @param killModel
     * @return
     */
    Result<String> kill(KillModel killModel);

    /**
     * log
     *
     * @param logModel
     * @return
     */
    Result<LogResultModel> log(LogModel logModel);

}
