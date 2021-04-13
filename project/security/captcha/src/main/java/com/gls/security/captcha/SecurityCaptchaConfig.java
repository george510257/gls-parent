package com.gls.security.captcha;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.support.ImagesCaptchaGenerator;
import com.gls.security.captcha.support.SmsCaptchaSender;
import com.gls.security.captcha.support.impl.DefaultSmsCaptchaSender;
import com.gls.security.captcha.support.impl.KaptchaImagesCaptchaGenerator;
import com.gls.security.captcha.web.repository.CaptchaRepository;
import com.gls.security.captcha.web.repository.impl.RedisCaptchaRepository;
import com.gls.security.captcha.web.repository.impl.SessionCaptchaRepository;
import com.gls.security.core.constants.SecurityConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author george
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(CaptchaProperties.class)
public class SecurityCaptchaConfig {

    @Bean
    @ConditionalOnMissingBean(ImagesCaptchaGenerator.class)
    public ImagesCaptchaGenerator imagesCaptchaGenerator(CaptchaProperties captchaProperties) {
        return new KaptchaImagesCaptchaGenerator(captchaProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SmsCaptchaSender.class)
    public SmsCaptchaSender smsCaptchaSender() {
        return new DefaultSmsCaptchaSender();
    }

    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX + ".captcha", name = "repository-type", havingValue = "redis", matchIfMissing = true)
    public static class RedisCaptchaRepositoryConfig {

        @Bean
        @ConditionalOnMissingBean(CaptchaRepository.class)
        public CaptchaRepository captchaRepository(RedisTemplate<Object, Object> redisTemplate, CaptchaProperties captchaProperties) {
            return new RedisCaptchaRepository(redisTemplate, captchaProperties);
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.SECURITY_PROPERTIES_PREFIX + ".captcha", name = "repository-type", havingValue = "session")
    public static class SessionCaptchaRepositoryConfig {

        @Bean
        @ConditionalOnMissingBean(CaptchaRepository.class)
        public CaptchaRepository captchaRepository() {
            return new SessionCaptchaRepository();
        }

    }
}
