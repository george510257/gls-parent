package com.gls.common.user.api.rpc;

import com.gls.common.user.api.model.ClientModel;

import java.util.List;

/**
 * @author george
 */
public interface ClientApi {

    /**
     * 获取客户端对象
     *
     * @param clientId 客户端id
     * @return 客户端对象
     */
    ClientModel loadClientByClientId(String clientId);

    /**
     * 添加客户端
     *
     * @param clientModel 客户端信息
     */
    void addClientDetails(ClientModel clientModel);

    /**
     * 更新客户端
     *
     * @param clientModel 客户端信息
     */
    void updateClientDetails(ClientModel clientModel);

    /**
     * 更新客户端密码
     *
     * @param clientId 客户端id
     * @param secret   客户端密码
     */
    void updateClientSecret(String clientId, String secret);

    /**
     * 删除客户端信息
     *
     * @param clientId 客户端id
     */
    void removeClientDetails(String clientId);

    /**
     * 获取所有客户端信息
     *
     * @return 客户端列表
     */
    List<ClientModel> listClientDetails();
}
