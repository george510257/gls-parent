package com.gls.demo.consumer.web.service.impl;

import com.gls.demo.consumer.web.service.FeignDemoService;
import com.gls.demo.provider.api.feign.impl.FeignDemoApiImpl;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class FeignDemoServiceImpl extends FeignDemoApiImpl implements FeignDemoService {
}