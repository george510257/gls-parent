package com.gls.starter.data.jpa.base;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author george
 */
@NoRepositoryBean
public interface BaseEntityRepository<Entity extends BaseEntity> extends BaseRepository<Entity, Long> {
}
