package com.gls.demo.consumer.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.gls.framework.core.exception.DefaultExceptionHandler;
import com.gls.framework.core.support.RpcServiceHelper;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author george
 */
@Configuration
@EnableFeignClients(basePackages = FeignConfig.FEIGN_BASE_PACKAGES)
public class FeignConfig {
    public static final String FEIGN_BASE_PACKAGES = "com.gls.demo.consumer.web.service";
    public static final String PROVIDER_APPLICATION_NAME = "gls-demo-provider-a";

    @Bean
    public WebMvcRegistrations feignWebRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return new RequestMappingHandlerMapping() {
                    @Override
                    protected boolean isHandler(Class<?> beanType) {
                        return super.isHandler(beanType)
                                && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
                    }
                };
            }
        };
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = DefaultExceptionHandler.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RpcServiceHelper rpcServiceHelper(ApplicationContext applicationContext, RegistryConfig registryConfig) {
        return new RpcServiceHelper(applicationContext, registryConfig);
    }
}
