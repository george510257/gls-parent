package com.gls.job.dashboard.web.repository;

import com.gls.job.dashboard.web.entity.BaseTriggerEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author george
 */
@NoRepositoryBean
public interface TriggerRepository<Trigger extends BaseTriggerEntity> extends BaseEntityRepository<Trigger> {

    /**
     * 通过name和group获取entity对象
     *
     * @param name
     * @param groupName
     * @return
     */
    Trigger findByNameAndGroupName(String name, String groupName);
}
