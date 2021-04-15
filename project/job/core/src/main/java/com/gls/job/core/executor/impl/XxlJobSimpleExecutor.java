package com.gls.job.core.executor.impl;

import com.gls.job.core.executor.XxlJobExecutor;
import com.gls.job.core.handler.annotation.XxlJob;
import com.gls.job.core.handler.impl.MethodJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * gls-job executor (for frameless)
 *
 * @author xuxueli 2020-11-05
 */
public class XxlJobSimpleExecutor extends XxlJobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobSimpleExecutor.class);

    private List<Object> glsJobBeanList = new ArrayList<>();

    public List<Object> getXxlJobBeanList() {
        return glsJobBeanList;
    }

    public void setXxlJobBeanList(List<Object> glsJobBeanList) {
        this.glsJobBeanList = glsJobBeanList;
    }

    public void start() {

        // init JobHandler Repository (for method)
        initJobHandlerMethodRepository(glsJobBeanList);

        // super start
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        super.destroy();
    }

    private void initJobHandlerMethodRepository(List<Object> glsJobBeanList) {
        if (glsJobBeanList == null || glsJobBeanList.size() == 0) {
            return;
        }

        // init job handler from method
        for (Object bean : glsJobBeanList) {
            // method
            Method[] methods = bean.getClass().getDeclaredMethods();
            if (methods == null || methods.length == 0) {
                continue;
            }
            for (Method executeMethod : methods) {

                // anno
                XxlJob glsJob = executeMethod.getAnnotation(XxlJob.class);
                if (glsJob == null) {
                    continue;
                }

                String name = glsJob.value();
                if (name.trim().length() == 0) {
                    throw new RuntimeException("gls-job method-jobhandler name invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                }
                if (loadJobHandler(name) != null) {
                    throw new RuntimeException("gls-job jobhandler[" + name + "] naming conflicts.");
                }

                // execute method
                /*if (!(method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(String.class))) {
                    throw new RuntimeException("gls-job method-jobhandler param-classtype invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                            "The correct method format like \" public ReturnT<String> execute(String param) \" .");
                }
                if (!method.getReturnType().isAssignableFrom(ReturnT.class)) {
                    throw new RuntimeException("gls-job method-jobhandler return-classtype invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                            "The correct method format like \" public ReturnT<String> execute(String param) \" .");
                }*/

                executeMethod.setAccessible(true);

                // init and destory
                Method initMethod = null;
                Method destroyMethod = null;

                if (glsJob.init().trim().length() > 0) {
                    try {
                        initMethod = bean.getClass().getDeclaredMethod(glsJob.init());
                        initMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("gls-job method-jobhandler initMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }
                if (glsJob.destroy().trim().length() > 0) {
                    try {
                        destroyMethod = bean.getClass().getDeclaredMethod(glsJob.destroy());
                        destroyMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("gls-job method-jobhandler destroyMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }

                // registry jobhandler
                registJobHandler(name, new MethodJobHandler(bean, executeMethod, initMethod, destroyMethod));

            }

        }

    }

}
