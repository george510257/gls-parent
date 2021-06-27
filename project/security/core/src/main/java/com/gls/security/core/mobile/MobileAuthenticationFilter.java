package com.gls.security.core.mobile;

import com.gls.security.core.constants.SecurityProperties;
import com.gls.security.core.mobile.token.MobileAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 手机登录过滤器
 *
 * @author george
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String HTTP_METHOD_POST = "POST";
    @Getter
    @Setter
    private String mobileParameter;
    @Getter
    @Setter
    private boolean postOnly = true;

    public MobileAuthenticationFilter(SecurityProperties securityProperties) {
        super(new AntPathRequestMatcher(securityProperties.getMobile().getLoginProcessingUrl(), HTTP_METHOD_POST));
        mobileParameter = securityProperties.getMobile().getMobileParameter();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !HTTP_METHOD_POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }
}
