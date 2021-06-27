package com.gls.demo.security.web.controller;

import com.gls.demo.security.support.DeferredResultHolder;
import com.gls.demo.security.support.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {
    @Resource
    private MockQueue mockQueue;

    @RequestMapping("/orderDeferredResult")
    public DeferredResult<String> orderDeferredResult() throws Exception {
        DeferredResult<String> result = new DeferredResult<>();
        log.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResultHolder.addDeferredResult(orderNumber, result);
        return result;
    }

    @RequestMapping("/orderCallable")
    public Callable<String> orderCallable() throws Exception {
        log.info("主线程开始");
        Callable<String> result = () -> {
            log.info("副线程开始");
            Thread.sleep(500);
            log.info("副线程返回");
            return "success";
        };
        log.info("主线程返回");
        return result;
    }
}
