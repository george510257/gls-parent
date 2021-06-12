package com.gls.job.core.executor.handler;

import com.gls.job.core.common.base.BaseHolder;
import com.gls.job.core.executor.handler.annotation.Job;
import com.gls.job.core.executor.handler.impl.MethodJobHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class JobHandlerHolder extends BaseHolder<String, JobHandler> {

    private final ApplicationContext applicationContext;

    public void init() {
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
                    regist(name, new MethodJobHandler(bean, method, initMethod, destroyMethod), "");
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
