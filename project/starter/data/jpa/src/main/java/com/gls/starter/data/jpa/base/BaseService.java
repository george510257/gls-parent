package com.gls.starter.data.jpa.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 公共service
 *
 * @author george
 */
public interface BaseService<Model, QueryModel> {
    /**
     * getPage
     *
     * @param model
     * @param pageable
     * @return
     */
    Page<Model> getPage(QueryModel model, Pageable pageable);

    /**
     * add
     *
     * @param model
     */
    void add(Model model);

    /**
     * update
     *
     * @param model
     */
    void update(Model model);

    /**
     * remove
     *
     * @param id
     */
    void remove(Long id);
}
