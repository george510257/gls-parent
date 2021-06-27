package com.gls.security.browser.session;

import com.gls.security.core.constants.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author george
 */
@Slf4j
public class DefaultExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private final SecurityProperties securityProperties;
    private final SimpleRedirectSessionInformationExpiredStrategy simpleRedirectSessionInformationExpiredStrategy;

    public DefaultExpiredSessionStrategy(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.simpleRedirectSessionInformationExpiredStrategy = new SimpleRedirectSessionInformationExpiredStrategy(securityProperties.getSession().getInvalidSessionUrl());
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.info("Expired Session Strategy");
        simpleRedirectSessionInformationExpiredStrategy.onExpiredSessionDetected(event);
    }
}
