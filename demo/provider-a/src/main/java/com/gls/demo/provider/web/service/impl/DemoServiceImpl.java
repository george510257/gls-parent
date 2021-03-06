package com.gls.demo.provider.web.service.impl;

import com.gls.demo.provider.api.model.DemoModel;
import com.gls.demo.provider.web.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Service(value = "demoService")
public class DemoServiceImpl implements DemoService {
    private static final Map<Integer, DemoModel> DEMO_MODEL_MAP = new HashMap<>();

    @Override
    public List<DemoModel> query(DemoModel demoModel) {
        log.info("query model: " + demoModel.toString());
        return new ArrayList<>(DEMO_MODEL_MAP.values());
    }

    @Override
    public DemoModel getInfo(Integer id) {
        log.info("getInfo id: " + id);
        return DEMO_MODEL_MAP.get(id);
    }

    @Override
    public DemoModel create(DemoModel model) {
        log.info("create model: " + model.toString());
        return DEMO_MODEL_MAP.put(model.getId(), model);
    }

    @Override
    public DemoModel update(DemoModel model) {
        log.info("update model: " + model.toString());
        return DEMO_MODEL_MAP.put(model.getId(), model);
    }

    @Override
    public void delete(Integer id) {
        log.info("delete id: " + id);
        DEMO_MODEL_MAP.remove(id);
    }
}
