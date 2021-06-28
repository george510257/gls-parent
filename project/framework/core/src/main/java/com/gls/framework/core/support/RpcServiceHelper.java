package com.gls.framework.core.support;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class RpcServiceHelper {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private RegistryConfig registryConfig;

    public <T> T getServiceByGroup(String group, Class<T> serviceClass) {
        ReferenceBean<T> referenceBean = new ReferenceBean<>();
        referenceBean.setApplicationContext(applicationContext);
        referenceBean.setRegistry(registryConfig);
        referenceBean.setGroup(group);
        referenceBean.setInterface(serviceClass);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        return cache.get(referenceBean);
    }

    public <T> void registService(String group, Class<T> cls, T t) {
        ServiceBean<T> serviceBean = new ServiceBean<>();
        serviceBean.setApplicationContext(applicationContext);
        serviceBean.setInterface(cls);
        serviceBean.setGroup(group);
        serviceBean.setRef(t);
        serviceBean.setRegistry(registryConfig);
        serviceBean.export();
    }
}
