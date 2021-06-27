package com.gls.starter.data.jpa;

import com.gls.framework.core.constants.FrameworkConstants;
import com.gls.framework.core.support.SecurityHelper;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author george
 */
@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = FrameworkConstants.ENTITY_BASE_PACKAGE)
@EnableJpaRepositories(basePackages = FrameworkConstants.REPOSITORY_BASE_PACKAGE, repositoryBaseClass = BaseRepositoryImpl.class)
public class DataJpaConfig {
    @Resource
    private SecurityHelper securityHelper;

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            Long loginUserId = securityHelper.getLoginUserId();
            if (!ObjectUtils.isEmpty(loginUserId)) {
                return Optional.of(loginUserId);
            }
            return Optional.empty();
        };
    }
}
