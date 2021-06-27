package com.gls.security.captcha.filter;

import com.gls.security.captcha.exception.CaptchaException;
import com.gls.security.captcha.holder.CaptchaServiceHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter {
    private final CaptchaServiceHolder captchaServiceHolder;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            captchaServiceHolder.getAllCaptchaServices().values().forEach(captchaService -> {
                if (captchaService.isValidate()) {
                    captchaService.validate();
                }
            });
            filterChain.doFilter(request, response);
        } catch (CaptchaException exception) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        }
    }
}
