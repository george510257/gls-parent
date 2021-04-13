package com.gls.framework.core.support;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Properties;

/**
 * @author george
 */
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    private static final String YML_SUFFIX = ".yml";

    private static final String YAML_SUFFIX = ".yaml";

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        String sourceName = name != null ? name : resource.getResource().getFilename();
        assert sourceName != null;
        if (!resource.getResource().exists()) {
            return new PropertiesPropertySource(sourceName, new Properties());
        }
        if (sourceName.endsWith(YML_SUFFIX) || sourceName.endsWith(YAML_SUFFIX)) {
            Properties propertiesFromYaml = loadYml(resource);
            return new PropertiesPropertySource(sourceName, propertiesFromYaml);
        }
        return super.createPropertySource(name, resource);
    }

    private Properties loadYml(EncodedResource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
