package com.gls.starter.data.jpa.base;

import com.gls.framework.api.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 公共service
 *
 * @author george
 */
public interface BaseService<Model extends BaseModel, QueryModel> {
    /**
     * getAll
     *
     * @return
     */
    List<Model> getAll();

    /**
     * getById
     *
     * @param id
     * @return
     */
    Model getById(Long id);

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

    /**
     * remove all
     *
     * @param ids
     */
    void removeAll(List<Long> ids);
}
