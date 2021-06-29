package com.gls.job.admin.web.service;

import com.gls.job.admin.web.model.query.QueryJobRegistry;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.starter.data.jpa.base.BaseService;

/**
 * @author george
 */
public interface JobRegistryService extends BaseService<RegistryModel, QueryJobRegistry> {
    /**
     * doJobRegistry
     */
    void doJobRegistry();
}
