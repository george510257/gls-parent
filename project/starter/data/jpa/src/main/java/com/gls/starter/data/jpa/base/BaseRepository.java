package com.gls.starter.data.jpa.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * @author george
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepositoryImplementation<T, ID> {

    /**
     * find one by example
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> Optional<S> findOne(S example);

    /**
     * count by example
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> long count(S example);

    /**
     * exists by example
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> boolean exists(S example);

    /**
     * find all by example
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(S example);

    /**
     * find all by example
     *
     * @param example
     * @param sort
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(S example, Sort sort);

    /**
     * find all by example
     *
     * @param example
     * @param pageable
     * @param <S>
     * @return
     */
    <S extends T> Page<S> findAll(S example, Pageable pageable);
}
