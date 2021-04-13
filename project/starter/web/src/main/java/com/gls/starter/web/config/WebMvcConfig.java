package com.gls.starter.web.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author george
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(httpMessageConverter -> {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) httpMessageConverter;
                stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
            } else if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) httpMessageConverter;
                mappingJackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
