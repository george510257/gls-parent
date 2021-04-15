package com.gls.common.user.config;

import com.gls.common.user.api.model.ClientModel;
import com.gls.common.user.api.model.RoleModel;
import com.gls.common.user.api.model.UserModel;
import com.gls.common.user.constants.UserDefaultProperties;
import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Configuration
@EnableConfigurationProperties(UserDefaultProperties.class)
public class DefaultConfig {

    @Resource
    private UserDefaultProperties userDefaultProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserModel defaultUserModel() {
        UserDefaultProperties.User user = userDefaultProperties.getUser();
        UserModel userModel = new UserModel();
        userModel.setPassword(passwordEncoder().encode(user.getPassword()));
        userModel.setUsername(user.getName());
        userModel.setRoles(getRolesList(user.getRoles()));
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        return userModel;
    }

    private List<RoleModel> getRolesList(List<String> roles) {
        return roles.stream().map(s -> {
            RoleModel roleModel = new RoleModel();
            roleModel.setRole(s);
            return roleModel;
        }).collect(Collectors.toList());
    }

    @Bean
    public ClientModel defaultClientModel() {
        UserDefaultProperties.Client client = userDefaultProperties.getClient();
        ClientModel clientModel = new ClientModel();
        clientModel.setClientId(client.getClientId());
        clientModel.setClientSecret(passwordEncoder().encode(client.getClientSecret()));
        clientModel.setScope(client.getScope());
        clientModel.setResourceIds(client.getResourceIds());
        clientModel.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
        clientModel.setRegisteredRedirectUris(client.getRegisteredRedirectUris());
        clientModel.setAutoApproveScopes(client.getAutoApproveScopes());
        clientModel.setRoles(getRolesList(client.getRoles()));
        clientModel.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        clientModel.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        clientModel.setAdditionalInformation(Maps.newHashMap());
        return clientModel;
    }
}
