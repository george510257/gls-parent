package com.gls.demo.provider.web.controller;

import com.gls.demo.provider.api.feign.FeignDemoApi;
import com.gls.demo.provider.api.model.DemoModel;
import com.gls.demo.provider.web.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@RestController
public class FeignDemoController implements FeignDemoApi {
    @Resource
    private DemoService demoService;

    @Override
    public List<DemoModel> query(DemoModel model) {
        log.info("FeignDemoController query model:" + model.toString());
        return demoService.query(model);
    }

    @Override
    public DemoModel getInfo(Integer id) {
        log.info("FeignDemoController getInfo id:" + id);
        return demoService.getInfo(id);
    }

    @Override
    public DemoModel create(DemoModel model) {
        log.info("FeignDemoController create model:" + model.toString());
        return demoService.create(model);
    }

    @Override
    public DemoModel update(DemoModel model) {
        log.info("FeignDemoController update model:" + model.toString());
        return demoService.update(model);
    }

    @Override
    public void delete(Integer id) {
        log.info("FeignDemoController delete id:" + id);
        demoService.delete(id);
    }
}
