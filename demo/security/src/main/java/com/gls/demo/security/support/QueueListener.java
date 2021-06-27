package com.gls.demo.security.support;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author zhailiang
 */
@Slf4j
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private MockQueue mockQueue;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("queue-listener-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            while (true) {
                String orderNumber = mockQueue.getCompleteOrder();
                if (orderNumber != null && !orderNumber.isEmpty()) {
                    log.info("返回订单处理结果:" + orderNumber);
                    DeferredResultHolder.getDeferredResult(orderNumber).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        singleThreadPool.shutdown();
    }
}
