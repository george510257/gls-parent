package com.gls.job.core.executor.handler.builder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author xuxueli 2018-11-01
 */
@Slf4j
@AllArgsConstructor
public class SpringGlueBuilder extends GlueBuilder {

    private final ApplicationContext applicationContext;

    /**
     * inject action of spring
     *
     * @param instance
     */
    @Override
    protected void injectService(Object instance) {
        if (instance == null) {
            return;
        }

        if (applicationContext == null) {
            return;
        }

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object fieldBean = null;
            // with bean-id, bean could be found by both @Resource and @Autowired, or bean could only be found by @Autowired

            if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
                try {
                    Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
                    if (StringUtils.hasText(resource.name())) {
                        fieldBean = applicationContext.getBean(resource.name());
                    } else {
                        fieldBean = applicationContext.getBean(field.getName());
                    }
                } catch (Exception ignored) {
                }
                if (fieldBean == null) {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if (qualifier != null && qualifier.value().length() > 0) {
                    fieldBean = applicationContext.getBean(qualifier.value());
                } else {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            }

            if (fieldBean != null) {
                field.setAccessible(true);
                try {
                    field.set(instance, fieldBean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}
