package com.gls.demo.provider.web.rpc;

import com.gls.demo.provider.api.model.DemoModel;
import com.gls.demo.provider.api.rpc.DubboDemoApi;
import com.gls.demo.provider.web.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@DubboService(group = "gls-demo-provider-a")
public class DubboDemoRpcService implements DubboDemoApi {
    @Resource
    private DemoService demoService;

    @Override
    public List<DemoModel> query(DemoModel model) {
        return demoService.query(model);
    }

    @Override
    public DemoModel getInfo(Integer id) {
        return demoService.getInfo(id);
    }

    @Override
    public DemoModel create(DemoModel model) {
        return demoService.create(model);
    }

    @Override
    public DemoModel update(DemoModel model) {
        return demoService.update(model);
    }

    @Override
    public void delete(Integer id) {
        demoService.delete(id);
    }
}
