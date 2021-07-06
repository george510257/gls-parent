package com.gls.job.executor.core.handler.impl;

import com.gls.job.executor.core.handler.JobHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

/**
 * @author xuxueli 2019-12-11 21:12:18
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class MethodJobHandler implements JobHandler {
    private final Object target;
    private final Method method;
    private final Method initMethod;
    private final Method destroyMethod;

    @Override
    public void destroy() throws Exception {
        if (destroyMethod != null) {
            destroyMethod.invoke(target);
        }
    }

    @Override
    public void execute() throws Exception {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length > 0) {
            method.invoke(target, new Object[paramTypes.length]);
            // method-param can not be primitive-types
        } else {
            method.invoke(target);
        }
    }

    @Override
    public void init() throws Exception {
        if (initMethod != null) {
            initMethod.invoke(target);
        }
    }
}
