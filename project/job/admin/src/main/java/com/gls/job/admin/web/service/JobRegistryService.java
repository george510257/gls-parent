package com.gls.job.admin.web.service;

import com.gls.job.core.api.model.RegistryModel;

/**
 * @author george
 */
public interface JobRegistryService {

    /**
     * registry
     *
     * @param registryModel
     */
    void registry(RegistryModel registryModel);

    /**
     * registryRemove
     *
     * @param registryModel
     */
    void registryRemove(RegistryModel registryModel);

    /**
     * do JobRegistry
     */
    void doJobRegistry();
}
