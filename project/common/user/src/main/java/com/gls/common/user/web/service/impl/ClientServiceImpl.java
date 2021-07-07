package com.gls.common.user.web.service.impl;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.api.model.query.QueryClientModel;
import com.gls.common.user.web.converter.ClientConverter;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.common.user.web.repository.ClientRepository;
import com.gls.common.user.web.service.ClientService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Service
public class ClientServiceImpl
        extends BaseServiceImpl<ClientRepository, ClientConverter, ClientEntity, ClientModel, QueryClientModel>
        implements ClientService {
    @Resource
    private ClientModel defaultClientModel;
    @Resource
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository repository, ClientConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<ClientEntity> getSpec(QueryClientModel queryClientModel) {
        return null;
    }

    @Override
    public ClientModel loadClientByClientId(String clientId) {
        return converter.sourceToTarget(repository.getOneByClientId(clientId));
    }

    @Override
    public void addClientDetails(ClientModel clientModel) {
        add(clientModel);
    }

    @Override
    public void updateClientDetails(ClientModel clientModel) {
        ClientEntity clientEntity = repository.getOneByClientId(clientModel.getClientId());
        converter.copyTargetToSource(clientModel, clientEntity);
        repository.save(clientEntity);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) {
        ClientEntity clientEntity = repository.getOneByClientId(clientId);
        clientEntity.setClientSecret(passwordEncoder.encode(secret));
        repository.save(clientEntity);
    }

    @Override
    public void removeClientDetails(String clientId) {
        repository.deleteAllByClientId(clientId);
    }

    @Override
    public List<ClientModel> listClientDetails() {
        return getAll();
    }

    @Override
    public void saveDefaultClient() {
        ClientEntity clientEntity = converter.targetToSource(defaultClientModel);
        repository.deleteAllByClientId(clientEntity.getClientId());
        repository.flush();
        repository.saveAndFlush(clientEntity);
    }
}
