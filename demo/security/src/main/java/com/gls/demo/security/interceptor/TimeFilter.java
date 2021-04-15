package com.gls.demo.security.interceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author george
 */
@Slf4j
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TimeFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("TimeFilter start");
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        log.info("TimeFilter 耗时:" + (System.currentTimeMillis() - start));
        log.info("TimeFilter finish");
    }

    @Override
    public void destroy() {
        log.info("TimeFilter destroy");
    }
}
