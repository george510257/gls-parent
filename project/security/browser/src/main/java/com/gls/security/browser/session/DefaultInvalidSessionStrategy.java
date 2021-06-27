package com.gls.security.browser.session;

import com.gls.security.core.constants.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author george
 */
@Slf4j
public class DefaultInvalidSessionStrategy implements InvalidSessionStrategy {
    private final SecurityProperties securityProperties;
    private final SimpleRedirectInvalidSessionStrategy simpleRedirectInvalidSessionStrategy;

    public DefaultInvalidSessionStrategy(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.simpleRedirectInvalidSessionStrategy = new SimpleRedirectInvalidSessionStrategy(securityProperties.getSession().getInvalidSessionUrl());
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Default Invalid Session Strategy");
        simpleRedirectInvalidSessionStrategy.onInvalidSessionDetected(request, response);
    }
}
