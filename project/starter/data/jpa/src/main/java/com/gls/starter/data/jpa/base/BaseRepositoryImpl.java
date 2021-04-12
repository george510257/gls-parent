package com.gls.starter.data.jpa.base;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author george
 */
public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public <S extends T> Optional<S> findOne(S example) {
        return findOne(Example.of(example));
    }

    @Override
    public <S extends T> long count(S example) {
        return count(Example.of(example));
    }

    @Override
    public <S extends T> boolean exists(S example) {
        return exists(Example.of(example));
    }

    @Override
    public <S extends T> List<S> findAll(S example) {
        return findAll(Example.of(example));
    }

    @Override
    public <S extends T> List<S> findAll(S example, Sort sort) {
        return findAll(Example.of(example), sort);
    }

    @Override
    public <S extends T> Page<S> findAll(S example, Pageable pageable) {
        return findAll(Example.of(example), pageable);
    }
}
