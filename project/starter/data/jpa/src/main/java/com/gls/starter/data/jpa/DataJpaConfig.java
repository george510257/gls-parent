package com.gls.starter.data.jpa;

import com.gls.framework.core.constants.FrameworkCoreConstants;
import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author george
 */
@Configuration
@EntityScan(basePackages = FrameworkCoreConstants.ENTITY_BASE_PACKAGE)
@EnableJpaRepositories(basePackages = FrameworkCoreConstants.REPOSITORY_BASE_PACKAGE, repositoryBaseClass = BaseRepositoryImpl.class)
public class DataJpaConfig {
}
