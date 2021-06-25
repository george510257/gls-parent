package com.gls.job.admin.web.service;

import com.gls.job.core.api.model.RegistryModel;

import java.util.List;

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
     * doJobRegistry
     */
    void doJobRegistry();

    /**
     * getAddressList
     *
     * @param appname
     * @return
     */
    List<String> getAddressList(String appname);
}
