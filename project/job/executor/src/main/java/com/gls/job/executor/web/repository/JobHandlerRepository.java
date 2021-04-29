package com.gls.job.executor.web.repository;

import com.gls.job.executor.core.handler.JobHandler;
import com.gls.job.executor.core.handler.annotation.Job;
import com.gls.job.executor.core.handler.impl.MethodJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author georg
 */
@Slf4j
public class JobHandlerRepository {

    private static final ConcurrentMap<String, JobHandler> JOB_HANDLER_CONCURRENT_MAP = new ConcurrentHashMap<>();

    public JobHandler loadJobHandler(String name) {
        return JOB_HANDLER_CONCURRENT_MAP.get(name);
    }

    public void registJobHandler(Map<String, JobHandler> jobHandlerMap) {
        jobHandlerMap.forEach((key, value) -> log.info(">>>>>>>>>>> job register jobHandler success, name:{}, jobHandler:{}", key, value));
        JOB_HANDLER_CONCURRENT_MAP.putAll(jobHandlerMap);
    }

    public JobHandler registJobHandler(String name, JobHandler jobHandler) {
        log.info(">>>>>>>>>>> gls-job register jobHandler success, name:{}, jobHandler:{}", name, jobHandler);
        return JOB_HANDLER_CONCURRENT_MAP.put(name, jobHandler);
    }

    public void init(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            log.info(">>>>>>>>>>> applicationContext is null");
        } else {
            Collection<Object> beans = applicationContext.getBeansOfType(Object.class, false, true).values();
            log.info(">>>>>>>>>>> beans sizes: {}", beans.size());
            init(beans);
        }
    }

    public void init(Collection<Object> beans) {
        for (Object bean : beans) {
            Map<Method, Job> methodJobs = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<Job>) method -> AnnotatedElementUtils.findMergedAnnotation(method, Job.class));
            if (!methodJobs.isEmpty()) {
                for (Map.Entry<Method, Job> entry : methodJobs.entrySet()) {
                    Method method = entry.getKey();
                    Job job = entry.getValue();
                    String name = job.value();
                    Method initMethod = getMethod(bean, job.init());
                    Method destroyMethod = getMethod(bean, job.destroy());
                    registJobHandler(name, new MethodJobHandler(bean, method, initMethod, destroyMethod));
                }
            }
        }
    }

    private Method getMethod(Object bean, String methodName) {
        try {
            return bean.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error(">>>>>>>>>>> " + bean.getClass().getName() + ":" + methodName + "不存在!!", e);
        }
        return null;
    }
}
