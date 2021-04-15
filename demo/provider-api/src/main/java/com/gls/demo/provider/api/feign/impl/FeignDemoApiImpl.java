package com.gls.demo.provider.api.feign.impl;

import com.gls.demo.provider.api.feign.FeignDemoApi;
import com.gls.demo.provider.api.model.DemoModel;

import java.util.Collections;
import java.util.List;

/**
 * @author george
 */
public class FeignDemoApiImpl implements FeignDemoApi {

    private final DemoModel ERROR_MODEL = new DemoModel(0, "error");

    @Override
    public List<DemoModel> query(DemoModel model) {
        return Collections.singletonList(ERROR_MODEL);
    }

    @Override
    public DemoModel getInfo(Integer id) {
        return ERROR_MODEL;
    }

    @Override
    public DemoModel create(DemoModel model) {
        return ERROR_MODEL;
    }

    @Override
    public DemoModel update(DemoModel model) {
        return ERROR_MODEL;
    }

    @Override
    public void delete(Integer id) {

    }
}
