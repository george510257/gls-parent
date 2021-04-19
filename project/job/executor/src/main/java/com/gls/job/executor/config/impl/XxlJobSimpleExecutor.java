package com.gls.job.executor.config.impl;

import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.handler.annotation.XxlJob;
import com.gls.job.executor.handler.impl.MethodJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * gls-job executor (for frameless)
 *
 * @author george 2020-11-05
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

    @Override
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

    @Override
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
                    throw new RuntimeException("gls-job method-jobHandler name invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                }
                if (loadJobHandler(name) != null) {
                    throw new RuntimeException("gls-job jobHandler[" + name + "] naming conflicts.");
                }

                // execute method
                /*if (!(method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(String.class))) {
                    throw new RuntimeException("gls-job method-jobHandler param-classtype invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                            "The correct method format like \" public ReturnT<String> execute(String param) \" .");
                }
                if (!method.getReturnType().isAssignableFrom(ReturnT.class)) {
                    throw new RuntimeException("gls-job method-jobHandler return-classtype invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                            "The correct method format like \" public ReturnT<String> execute(String param) \" .");
                }*/

                executeMethod.setAccessible(true);

                // init and destroy
                Method initMethod = null;
                Method destroyMethod = null;

                if (glsJob.init().trim().length() > 0) {
                    try {
                        initMethod = bean.getClass().getDeclaredMethod(glsJob.init());
                        initMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("gls-job method-jobHandler initMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }
                if (glsJob.destroy().trim().length() > 0) {
                    try {
                        destroyMethod = bean.getClass().getDeclaredMethod(glsJob.destroy());
                        destroyMethod.setAccessible(true);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("gls-job method-jobHandler destroyMethod invalid, for[" + bean.getClass() + "#" + executeMethod.getName() + "] .");
                    }
                }

                // registry jobHandler
                registJobHandler(name, new MethodJobHandler(bean, executeMethod, initMethod, destroyMethod));

            }

        }

    }

}
