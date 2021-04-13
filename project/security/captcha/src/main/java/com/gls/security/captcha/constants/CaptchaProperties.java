package com.gls.security.captcha.constants;

import com.gls.security.core.constants.SecurityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author george
 */
@Data
@ConfigurationProperties(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX + ".captcha")
public class CaptchaProperties {

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    private static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imagesCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    private static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    private static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    private String repositoryType = "redis";

    private String deviceIdParameter = "deviceId";

    private Images images = new Images();

    private Sms sms = new Sms();

    @Data
    public static class Images {

        private String codeParameter = DEFAULT_PARAMETER_NAME_CODE_IMAGE;

        /**
         * 图片宽
         */
        private int width = 120;
        /**
         * 图片高
         */
        private int height = 32;

        /**
         * 验证码长度
         */
        private int length = 5;

        /**
         * 字体大小
         */
        private int fontSize = 30;

        /**
         * 过期时间
         */
        private int expireIn = 60;
        /**
         * 要拦截的url，多个url用逗号隔开，ant pattern
         */
        private Set<String> urls = new HashSet<>();
    }

    @Data
    public static class Sms {

        private String mobileParameter = DEFAULT_PARAMETER_NAME_MOBILE;

        private String codeParameter = DEFAULT_PARAMETER_NAME_CODE_SMS;

        /**
         * 验证码长度
         */
        private int length = 6;
        /**
         * 过期时间
         */
        private int expireIn = 60;
        /**
         * 要拦截的url，多个url用逗号隔开，ant pattern
         */
        private Set<String> urls = new HashSet<>();
    }
}
