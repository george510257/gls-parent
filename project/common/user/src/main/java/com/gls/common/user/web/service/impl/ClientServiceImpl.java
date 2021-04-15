package com.gls.common.user.web.service.impl;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.web.converter.ClientConverter;
import com.gls.common.user.web.entity.ClientEntity;
import com.gls.common.user.web.repository.ClientRepository;
import com.gls.common.user.web.service.ClientService;
import com.gls.common.user.web.service.RoleService;
import com.gls.framework.core.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service(value = "clientService")
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "client")
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientModel defaultClientModel;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleService roleService;

    @Resource
    private ClientRepository clientRepository;

    @Resource
    private ClientConverter clientConverter;

    @Override
    @Cacheable(key = "#root.methodName+'['+#clientId+']'")
    public ClientModel loadClientByClientId(String clientId) {
        ClientEntity info = clientRepository.getOneByClientId(clientId);
        ClientModel model = clientConverter.sourceToTarget(info);
        return model;
    }

    @Override
    public void addClientDetails(ClientModel model) {
        ClientEntity info = clientConverter.targetToSource(model);
        clientRepository.save(info);
    }

    @Override
    public void updateClientDetails(ClientModel model) {
        ClientEntity client = clientConverter.targetToSource(model);
        updateByClientId(client);
    }

    private void updateByClientId(ClientEntity client) {
        ClientEntity info = clientRepository.getOneByClientId(client.getClientId());
        info = BeanUtils.combine(client, info);
        clientRepository.save(info);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) {
        ClientEntity client = new ClientEntity();
        client.setClientId(clientId);
        client.setClientSecret(passwordEncoder.encode(secret));
        updateByClientId(client);
    }

    @Override
    public void removeClientDetails(String clientId) {
        clientRepository.deleteAllByClientId(clientId);
    }

    @Override
    public List<ClientModel> listClientDetails() {
        List<ClientEntity> infos = clientRepository.findAll();
        return clientConverter.sourceToTarget(infos);
    }

    @Override
    public void saveDefaultClient() {
        ClientEntity client = clientConverter.targetToSource(defaultClientModel);
        String clientId = defaultClientModel.getClientId();
        log.info("clientId: " + clientId);
        clientRepository.deleteAllByClientId(clientId);
        clientRepository.flush();
        client.setRoles(roleService.loadRoles(client.getRoles()));
        log.info("client: " + client.toString());
        clientRepository.saveAndFlush(client);
    }
}