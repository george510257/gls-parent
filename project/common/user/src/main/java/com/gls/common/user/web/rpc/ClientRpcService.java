package com.gls.common.user.web.rpc;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.api.rpc.ClientApi;
import com.gls.common.user.web.service.ClientService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@DubboService
public class ClientRpcService implements ClientApi {

    @Resource
    private ClientService clientService;

    @Override
    public ClientModel loadClientByClientId(String clientId) {
        return clientService.loadClientByClientId(clientId);
    }

    @Override
    public void addClientDetails(ClientModel clientModel) {
        clientService.addClientDetails(clientModel);
    }

    @Override
    public void updateClientDetails(ClientModel clientModel) {
        clientService.updateClientDetails(clientModel);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) {
        clientService.updateClientSecret(clientId, secret);
    }

    @Override
    public void removeClientDetails(String clientId) {
        clientService.removeClientDetails(clientId);
    }

    @Override
    public List<ClientModel> listClientDetails() {
        return clientService.listClientDetails();
    }
}
