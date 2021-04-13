package com.gls.starter.web.result;

import com.alibaba.fastjson.JSON;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.constants.FrameworkConstants;
import com.gls.framework.core.result.ResultHelper;
import com.gls.framework.core.result.ResultProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 统一返回数据、统一异常处理
 *
 * @author george
 */
@Slf4j
@ControllerAdvice(basePackages = FrameworkConstants.BASE_PACKAGE)
public class ResultHandler implements ResponseBodyAdvice<Object> {

    @Resource
    private ResultProperties resultProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        List<String> resultMethods = resultProperties.getMethods();

        Method method = returnType.getMethod();
        assert method != null;
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String name = className + "#" + methodName;

        for (String resultMethod : resultMethods) {
            if (name.startsWith(resultMethod)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        log.info("-----beforeBodyWrite-----");
        Result result;
        if (body == null) {
            result = ResultHelper.success();
        } else if (body instanceof Result) {
            result = (Result) body;
        } else {
            result = ResultHelper.success(body);
        }

        if (log.isDebugEnabled()) {
            log.debug("result: {}", JSON.toJSONString(result));
        }
        if (body instanceof String) {
            return JSON.toJSONString(result);
        }
        return result;
    }

}
