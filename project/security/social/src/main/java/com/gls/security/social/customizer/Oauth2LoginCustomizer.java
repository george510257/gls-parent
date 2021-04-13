package com.gls.security.social.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class Oauth2LoginCustomizer implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {

//    @Resource
//    private SocialProperties socialProperties;
//
//    @Resource
//    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
//
//    @Resource
//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;
//
//    @Resource
//    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
//
//    @Resource
//    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient;
//
//    @Resource
//    private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService;
//
//    @Resource
//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
//
//    @Resource
//    private GrantedAuthoritiesMapper userAuthoritiesMapper;

    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> oAuth2LoginConfigurer) {
//        oAuth2LoginConfigurer
//                .loginPage(socialProperties.getLoginPage())
//                .loginProcessingUrl(socialProperties.getLoginProcessingUrl())
//                .authorizedClientRepository(oAuth2AuthorizedClientRepository)
//                .authorizationEndpoint(authorizationEndpointConfig -> {
//                    authorizationEndpointConfig
//                            .baseUri(socialProperties.getAuthorizationRequestBaseUri())
//                            .authorizationRequestResolver(authorizationRequestResolver)
//                            .authorizationRequestRepository(authorizationRequestRepository);
//                })
//                .tokenEndpoint(tokenEndpointConfig -> {
//                    tokenEndpointConfig
//                            .accessTokenResponseClient(accessTokenResponseClient);
//                })
//                .userInfoEndpoint(userInfoEndpointConfig -> {
//                    userInfoEndpointConfig
//                            .userService(userService)
//                            .oidcUserService(oidcUserService)
//                            .userAuthoritiesMapper(userAuthoritiesMapper);
//                })
//                .redirectionEndpoint(redirectionEndpointConfig -> {
//                    redirectionEndpointConfig
//                            .baseUri(socialProperties.getAuthorizationResponseBaseUri());
//                });
    }
}
