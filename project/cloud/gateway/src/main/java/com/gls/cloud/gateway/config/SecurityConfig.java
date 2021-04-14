package com.gls.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author george
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String API_DOC_PATH = "/*/v3/api-docs";
    private static final String AUTH_SERVER_PATH = "/gls-common-auth/oauth/token";
    @Value("${springfox.documentation.swagger-ui.base-url}")
    private String baseUrl;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange()
                .pathMatchers(baseUrl + "/**").permitAll()
                .pathMatchers(API_DOC_PATH).permitAll()
                .pathMatchers(AUTH_SERVER_PATH).permitAll()
                .anyExchange()
                .authenticated()
                .and()

                .csrf().disable()

                .oauth2ResourceServer()
                .jwt()
                .and()
                .and()

                .build();
    }

}