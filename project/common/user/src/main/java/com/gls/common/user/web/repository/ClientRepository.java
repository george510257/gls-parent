package com.gls.common.user.web.repository;

import com.gls.common.user.web.entity.ClientEntity;
import com.gls.starter.data.jpa.base.BaseEntityRepository;

/**
 * @author george
 */
public interface ClientRepository extends BaseEntityRepository<ClientEntity> {

    /**
     * 通过clientId获取Client对象
     *
     * @param clientId
     * @return
     */
    ClientEntity getOneByClientId(String clientId);

    /**
     * 通过clientId删除Client对象
     *
     * @param clientId
     * @return
     */
    Integer deleteAllByClientId(String clientId);
}
