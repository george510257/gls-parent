package com.gls.demo.consumer.web.controller;

import com.gls.demo.consumer.web.service.FeignDemoService;
import com.gls.demo.provider.api.model.DemoModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@RestController
@RequestMapping(value = "/consumer/feign/demo")
public class FeignDemoController {
    @Resource
    private FeignDemoService feignDemoService;

    /**
     * demo 查询请求
     *
     * @param model
     * @return
     */
    @GetMapping
    public List<DemoModel> query(DemoModel model) {
        return feignDemoService.query(model);
    }

    /**
     * demo 获取详细信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id:\\d+}")
    public DemoModel getInfo(@PathVariable Integer id) {
        return feignDemoService.getInfo(id);
    }

    /**
     * demo 新增请求
     *
     * @param model
     * @return
     */
    @PostMapping
    public DemoModel create(@RequestBody DemoModel model) {
        return feignDemoService.create(model);
    }

    /**
     * demo 更新请求
     *
     * @param model
     * @return
     */
    @PutMapping
    public DemoModel update(@RequestBody DemoModel model) {
        return feignDemoService.update(model);
    }

    /**
     * demo 删除请求
     *
     * @param id
     */
    @DeleteMapping(value = "/{id:\\d+}")
    public void delete(@PathVariable Integer id) {
        feignDemoService.delete(id);
    }
}
