package com.gls.job.admin.web.controller.resolver;

import com.gls.job.admin.core.util.JacksonUtil;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

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

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        if (!(ex instanceof JobException)) {
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
        Result<String> errorResult = new Result<String>(Result.FAIL_CODE, ex.toString().replaceAll("\n", "<br/>"));

        // response
        ModelAndView mv = new ModelAndView();
        if (isJson) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JacksonUtil.writeValueAsString(errorResult));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            return mv;
        } else {

            mv.addObject("exceptionMsg", errorResult.getMsg());
            mv.setViewName("/common/common.exception");
            return mv;
        }
    }

}