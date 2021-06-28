package com.gls.demo.consumer.web.controller;

import com.gls.demo.consumer.config.FeignConfig;
import com.gls.demo.provider.api.model.DemoModel;
import com.gls.demo.provider.api.rpc.DubboDemoApi;
import com.gls.framework.core.support.RpcServiceHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@RestController
@RequestMapping(value = "/consumer/dubbo/demo")
public class DubboDemoController {
    @Resource
    private RpcServiceHelper rpcServiceHelper;

    /**
     * demo 查询请求
     *
     * @param model
     * @return
     */
    @GetMapping
    public List<DemoModel> query(DemoModel model) {
        return rpcServiceHelper.getServiceByGroup(FeignConfig.PROVIDER_APPLICATION_NAME, DubboDemoApi.class).query(model);
    }

    /**
     * demo 获取详细信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id:\\d+}")
    public DemoModel getInfo(@PathVariable Integer id) {
        return rpcServiceHelper.getServiceByGroup(FeignConfig.PROVIDER_APPLICATION_NAME, DubboDemoApi.class).getInfo(id);
    }

    /**
     * demo 新增请求
     *
     * @param model
     * @return
     */
    @PostMapping
    public DemoModel create(@RequestBody DemoModel model) {
        return rpcServiceHelper.getServiceByGroup(FeignConfig.PROVIDER_APPLICATION_NAME, DubboDemoApi.class).create(model);
    }

    /**
     * demo 更新请求
     *
     * @param model
     * @return
     */
    @PutMapping
    public DemoModel update(@RequestBody DemoModel model) {
        return rpcServiceHelper.getServiceByGroup(FeignConfig.PROVIDER_APPLICATION_NAME, DubboDemoApi.class).update(model);
    }

    /**
     * demo 删除请求
     *
     * @param id
     */
    @DeleteMapping(value = "/{id:\\d+}")
    public void delete(@PathVariable Integer id) {
        rpcServiceHelper.getServiceByGroup(FeignConfig.PROVIDER_APPLICATION_NAME, DubboDemoApi.class).delete(id);
    }
}
