package com.gls.job.executor.config.impl;

import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.glue.GlueFactory;
import com.gls.job.executor.handler.annotation.XxlJob;
import com.gls.job.executor.handler.impl.MethodJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * gls-job executor (for spring)
 *
 * @author george 2018-11-01 09:24:52
 */
public class XxlJobSpringExecutor extends XxlJobExecutor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobSpringExecutor.class);
    // ---------------------- applicationContext ----------------------
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /*private void initJobHandlerRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }

        // init job handler action
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);

        if (serviceBeanMap != null && serviceBeanMap.size() > 0) {
            for (Object serviceBean : serviceBeanMap.values()) {
                if (serviceBean instanceof IJobHandler) {
                    String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
                    IJobHandler handler = (IJobHandler) serviceBean;
                    if (loadJobHandler(name) != null) {
                        throw new RuntimeException("gls-job jobHandler[" + name + "] naming conflicts.");
                    }
                    registJobHandler(name, handler);
                }
            }
        }
    }*/

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        XxlJobSpringExecutor.applicationContext = applicationContext;
    }

    // start
    @Override
    public void afterSingletonsInstantiated() {

        // init JobHandler Repository
        /*initJobHandlerRepository(applicationContext);*/

        // init JobHandler Repository (for method)
        initJobHandlerMethodRepository(applicationContext);

        // refresh GlueFactory
        GlueFactory.refreshInstance(1);

        // super start
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // destroy
    @Override
    public void destroy() {
        super.destroy();
    }

    private void initJobHandlerMethodRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        // init job handler from method
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XxlJob> annotatedMethods = null;   // referred to ：org.springframework.context.event.EventListenerMethodProcessor.processBean
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        new MethodIntrospector.MetadataLookup<XxlJob>() {
                            @Override
                            public XxlJob inspect(Method method) {
                                return AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class);
                            }
                        });
            } catch (Throwable ex) {
                logger.error("gls-job method-jobHandler resolve error for bean[" + beanDefinitionName + "].", ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodXxlJobEntry.getKey();
                XxlJob glsJob = methodXxlJobEntry.getValue();
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