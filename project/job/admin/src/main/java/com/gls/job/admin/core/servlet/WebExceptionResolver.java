package com.gls.job.admin.core.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * common exception resolver
 *
 * @author xuxueli 2016-1-6 19:22:18
 */
@Slf4j
@Component
public class WebExceptionResolver implements HandlerExceptionResolver {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        if (!(ex instanceof GlsException)) {
            log.error("WebExceptionResolver:{}", ex);
        }
        // if json
        boolean isJson = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
            if (responseBody != null) {
                isJson = true;
            }
        }
        // error result
        Result<String> errorResult = new Result<>(Result.FAIL_CODE, ex.toString().replaceAll("\n", "<br/>"));
        // response
        ModelAndView mv = new ModelAndView();
        if (isJson) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(objectMapper.writeValueAsString(errorResult));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            return mv;
        } else {
            mv.addObject("exceptionMsg", errorResult.getMessage());
            mv.setViewName("/common/common.exception");
            return mv;
        }
    }
}