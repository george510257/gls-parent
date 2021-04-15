package com.gls.common.user.constants;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author george
 */
@Data
@Component
@ConfigurationProperties(prefix = UserDefaultProperties.PREFIX, ignoreInvalidFields = true)
public class UserDefaultProperties {

    public static final String PREFIX = FrameworkConstants.BASE_PROPERTIES_PREFIX + ".user.default";

    private User user = new User();

    private Client client = new Client();

    @Data
    public static class User {

        /**
         * Default user name.
         */
        private String name = "user";

        /**
         * Password for the default user name.
         */
        private String password = UUID.randomUUID().toString();

        /**
         * Granted roles for the default user name.
         */
        private List<String> roles = new ArrayList<>();
    }

    @Data
    public static class Client {

        /**
         * OAuth2 client id.
         */
        private String clientId;

        /**
         * OAuth2 client secret. A random secret is generated by default.
         */
        private String clientSecret = UUID.randomUUID().toString();

        private List<String> scope = new ArrayList<>();

        private List<String> resourceIds = new ArrayList<>();

        private List<String> authorizedGrantTypes = new ArrayList<>();

        private List<String> registeredRedirectUris = new ArrayList<>();

        private List<String> autoApproveScopes = new ArrayList<>();

        private List<String> roles = new ArrayList<>();

        private Integer accessTokenValiditySeconds;

        private Integer refreshTokenValiditySeconds;

    }
}
