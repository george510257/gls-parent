package com.gls.job.core.executor.web.repository;

import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author george
 */
public interface CallbackRepository {

    /**
     * save
     *
     * @param callbackModels
     */
    void save(List<CallbackModel> callbackModels);

    /**
     * get All
     *
     * @return
     */
    List<CallbackModel> getAll();
}
