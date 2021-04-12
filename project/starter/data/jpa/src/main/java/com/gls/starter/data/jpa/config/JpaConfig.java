package com.gls.starter.data.jpa.config;

import com.gls.starter.data.jpa.base.BaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author george
 */
@Configuration
@EntityScan(basePackages = JpaConfig.ENTITY_BASE_PACKAGE)
@EnableJpaRepositories(basePackages = JpaConfig.REPOSITORY_BASE_PACKAGE, repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaConfig {

    public static final String BASE_PACKAGE = "com.gls";

    public static final String ENTITY_BASE_PACKAGE = BASE_PACKAGE + ".**.domain";

    public static final String REPOSITORY_BASE_PACKAGE = BASE_PACKAGE + ".**.repository";
}
