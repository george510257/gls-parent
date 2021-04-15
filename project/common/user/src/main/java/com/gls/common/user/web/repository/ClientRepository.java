package com.gls.common.user.web.repository;

import com.gls.common.user.web.entity.ClientEntity;
import com.gls.starter.data.jpa.base.BaseRepository;

/**
 * @author george
 */
public interface ClientRepository extends BaseRepository<ClientEntity, Long> {

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
