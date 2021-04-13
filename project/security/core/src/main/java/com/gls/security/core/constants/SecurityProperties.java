package com.gls.security.core.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author george
 */
@Data
@ConfigurationProperties(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX)
public class SecurityProperties {

    /**
     * 默认的用户名密码登录请求处理url
     */
    private static final String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";
    /**
     * 默认的手机验证码登录请求处理url
     */
    private static final String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     * 默认的OPENID登录请求处理url
     */
    private static final String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";
    /**
     * 默认登录页面
     */
    private static final String DEFAULT_SIGN_IN_PAGE_URL = "/signIn.html";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    private static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     * openid参数名
     */
    private static final String DEFAULT_PARAMETER_NAME_OPEN_ID = "openId";
    /**
     * providerId参数名
     */
    private static final String DEFAULT_PARAMETER_NAME_PROVIDER_ID = "providerId";
    /**
     * session失效默认的跳转地址
     */
    private static final String DEFAULT_SESSION_INVALID_URL = "/session-invalid.html";
    /**
     * 获取第三方用户信息的url
     */
    private static final String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";

    private FormLogin formLogin = new FormLogin();

    private Session session = new Session();

    private Logout logout = new Logout();

    private RememberMe rememberMe = new RememberMe();

    private TokenStore tokenStore = new TokenStore();

    private Mobile mobile = new Mobile();

    @Data
    public static class FormLogin {

        private String loginPage = DEFAULT_SIGN_IN_PAGE_URL;

        private String loginProcessingUrl = DEFAULT_SIGN_IN_PROCESSING_URL_FORM;
    }

    @Data
    public static class Mobile {

        private String mobileParameter = DEFAULT_PARAMETER_NAME_MOBILE;

        private String loginProcessingUrl = DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE;
    }

    @Data
    public static class Session {

        /**
         * 同一个用户在系统中的最大session数，默认1
         */
        private int maximumSessions = 1;
        /**
         * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
         */
        private boolean maxSessionsPreventsLogin;
        /**
         * session失效时跳转的地址
         */
        private String invalidSessionUrl = DEFAULT_SESSION_INVALID_URL;
    }

    @Data
    public static class Logout {

        private String logoutUrl = "/signOut";

        private Set<String> deleteCookies = new HashSet<>(Collections.singleton("JSESSIONID"));

    }

    @Data
    public static class RememberMe {

        /**
         * '记住我'功能的有效时间，默认1小时
         */
        private int tokenValiditySeconds = 3600;
    }

    @Data
    public static class TokenStore {

        private String type = "redis";

        private Jwt jwt = new Jwt();
    }

    @Data
    public static class Jwt {

        private String keyStoreFile = "glseven.jks";

        private String keyStorePassword = "iflytek123";

        private String keyAlias = "glseven-jwt";

        private String keyPassword = "glseven123";

    }
}
