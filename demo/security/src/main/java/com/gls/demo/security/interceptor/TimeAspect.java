package com.gls.demo.security.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author george
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.gls.demo.security.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        log.info("TimeAspect start");

        Arrays.stream(pjp.getArgs()).forEach(o -> {
            log.info("arg class name is {}", o.getClass().getName());
            log.info("arg is {}", o);
        });

        long start = System.currentTimeMillis();

        Object object = pjp.proceed();

        log.info("TimeAspect 耗时:" + (System.currentTimeMillis() - start));

        log.info("TimeAspect end");

        return object;
    }

}
