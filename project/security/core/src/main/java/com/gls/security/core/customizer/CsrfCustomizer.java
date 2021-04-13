package com.gls.security.core.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CsrfCustomizer implements Customizer<CsrfConfigurer<HttpSecurity>> {

    @Override
    public void customize(CsrfConfigurer<HttpSecurity> csrfConfigurer) {
        csrfConfigurer.disable();
    }
}
