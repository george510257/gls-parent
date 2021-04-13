package com.gls.framework.core.exception;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.constants.FrameworkConstants;
import com.gls.framework.core.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author george
 */
@Slf4j
@ControllerAdvice(basePackages = FrameworkConstants.BASE_PACKAGE)
public class DefaultExceptionHandler {

    public static SentinelClientHttpResponse handleException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        log.error(ex.getMessage(), ex);
        return new SentinelClientHttpResponse("RestTemplate FallBack Msg");
    }

    /**
     * 空指针异常处理方法
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result<String> nullPointerException(NullPointerException exception) {
        log.info("-----nullPointerException-----");
        log.info("error message: {}", exception.getMessage());
        exception.printStackTrace();
        return ResultHelper.nullPointError(exception.getMessage());
    }

    /**
     * 客户端异常处理方法
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    public Result<String> httpClientErrorException(HttpClientErrorException exception) {
        log.info("-----httpClientErrorException-----");
        log.info("error message: {}", exception.getMessage());
        exception.printStackTrace();
        return ResultHelper.clientError(exception.getMessage());
    }

    /**
     * 未知异常处理方法
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<String> exception(Exception exception) {
        log.info("-----exception-----");
        log.info("error message: {}", exception.getMessage());
        exception.printStackTrace();
        return ResultHelper.unknownError(exception.getMessage());
    }
}
