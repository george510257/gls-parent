package com.gls.demo.consumer.web.service;

import com.gls.demo.consumer.config.FeignConfig;
import com.gls.demo.consumer.web.service.impl.FeignDemoServiceImpl;
import com.gls.demo.provider.api.FeignDemoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author george
 */
@FeignClient(contextId = "feignDemoService", name = FeignConfig.PROVIDER_APPLICATION_NAME, fallback = FeignDemoServiceImpl.class)
public interface FeignDemoService extends FeignDemoApi {
}
