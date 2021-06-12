package com.gls.job.executor.handler.builder;

import com.gls.job.executor.handler.JobHandler;
import groovy.lang.GroovyClassLoader;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * glue factory, product class/object by name
 *
 * @author xuxueli 2016-1-2 20:02:27
 */
public class GlueBuilder {

    /**
     * groovy class loader
     */
    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    private final ConcurrentMap<String, Class<?>> classConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * load new instance, prototype
     *
     * @param codeSource
     * @return
     * @throws Exception
     */
    public JobHandler loadNewInstance(String codeSource) throws Exception {
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = getCodeSourceClass(codeSource);
            if (clazz != null) {
                Object instance = clazz.newInstance();
                if (instance instanceof JobHandler) {
                    this.injectService(instance);
                    return (JobHandler) instance;
                } else {
                    throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, "
                            + "cannot convert from instance[" + instance.getClass() + "] to JobHandler");
                }
            }
        }
        throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, instance is null");
    }

    private Class<?> getCodeSourceClass(String codeSource) {
        try {
            // md5
            byte[] md5 = MessageDigest.getInstance("MD5").digest(codeSource.getBytes());
            String md5Str = new BigInteger(1, md5).toString(16);

            Class<?> clazz = classConcurrentHashMap.get(md5Str);
            if (clazz == null) {
                clazz = groovyClassLoader.parseClass(codeSource);
                classConcurrentHashMap.putIfAbsent(md5Str, clazz);
            }
            return clazz;
        } catch (Exception e) {
            return groovyClassLoader.parseClass(codeSource);
        }
    }

    /**
     * inject service of bean field
     *
     * @param instance
     */
    protected void injectService(Object instance) {
        // do something
    }

}
