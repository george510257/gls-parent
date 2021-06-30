package com.gls.job.executor.web.repository;

import com.gls.job.core.api.model.CallbackModel;

import java.util.List;

/**
 * @author george
 */
public interface CallbackRepository {
    /**
     * get All
     *
     * @return
     */
    List<CallbackModel> getAll();

    /**
     * save
     *
     * @param callbackModels
     */
    void save(List<CallbackModel> callbackModels);
}
