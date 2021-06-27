package com.gls.demo.provider.web.controller;

import com.gls.demo.provider.api.model.DemoModel;
import com.gls.demo.provider.web.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping(value = "/provider/demo")
public class DemoController {
    @Resource
    private DemoService demoService;

    /**
     * demo 查询请求
     *
     * @param model
     * @return
     */
    @GetMapping
    public List<DemoModel> query(DemoModel model) {
        log.info("DemoController query model:" + model.toString());
        return demoService.query(model);
    }

    /**
     * demo 获取详细信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id:\\d+}")
    public DemoModel getInfo(@PathVariable Integer id) {
        log.info("DemoController getInfo id:" + id);
        return demoService.getInfo(id);
    }

    /**
     * demo 新增请求
     *
     * @param model
     * @return
     */
    @PostMapping
    public DemoModel create(@RequestBody DemoModel model) {
        log.info("DemoController create model:" + model.toString());
        return demoService.create(model);
    }

    /**
     * demo 更新请求
     *
     * @param model
     * @return
     */
    @PutMapping
    public DemoModel update(@RequestBody DemoModel model) {
        log.info("DemoController update model:" + model.toString());
        return demoService.update(model);
    }

    /**
     * demo 删除请求
     *
     * @param id
     */
    @DeleteMapping(value = "/{id:\\d+}")
    public void delete(@PathVariable Integer id) {
        log.info("DemoController delete id:" + id);
        demoService.delete(id);
    }
}
