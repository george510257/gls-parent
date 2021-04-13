package com.gls.framework.core.support;

import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.springframework.context.ApplicationContext;

/**
 * @author george
 */
public class RpcServiceHelper {

    private final ApplicationContext applicationContext;

    private final RegistryConfig registryConfig;

    public RpcServiceHelper(ApplicationContext applicationContext, RegistryConfig registryConfig) {
        this.applicationContext = applicationContext;
        this.registryConfig = registryConfig;
    }

    public <T> T loadService(String group, Class<T> cls) {
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        ReferenceBean<T> referenceBean = new ReferenceBean<T>();
        referenceBean.setApplicationContext(applicationContext);
        referenceBean.setInterface(cls);
        referenceBean.setGroup(group);
        referenceBean.setRegistry(registryConfig);
        try {
            referenceBean.afterPropertiesSet();
            return cache.get(referenceBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
