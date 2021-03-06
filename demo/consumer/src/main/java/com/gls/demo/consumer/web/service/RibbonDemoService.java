package com.gls.demo.consumer.web.service;

import com.gls.demo.provider.api.model.DemoModel;

import java.util.List;

/**
 * @author george
 */
public interface RibbonDemoService {
    /**
     * demo 查询请求
     *
     * @param model
     * @return
     */
    List<DemoModel> query(DemoModel model);

    /**
     * demo 获取详细信息
     *
     * @param id
     * @return
     */
    DemoModel getInfo(Integer id);

    /**
     * demo 新增请求
     *
     * @param model
     * @return
     */
    DemoModel create(DemoModel model);

    /**
     * demo 更新请求
     *
     * @param model
     * @return
     */
    DemoModel update(DemoModel model);

    /**
     * demo 删除请求
     *
     * @param id
     */
    void delete(Integer id);
}
