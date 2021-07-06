package com.gls.starter.data.jpa.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

/**
 * @author george
 */
@Slf4j
public class GlsImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
    private static final String PREFIX = "Tb";
    private static final String SUFFIX = "Entity";

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        String name = super.transformEntityName(entityNaming);
        log.info("name: {}", name);
        name = PREFIX + name.replaceAll(SUFFIX, "");
        return name;
    }
}
