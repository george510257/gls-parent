package com.gls.framework.core.result;

import com.gls.framework.api.result.Result;

/**
 * @author george
 */
public class ResultHelper {

    private static <Model> Result<Model> getResult(Boolean success, Integer code, String message, Model model) {
        Result.ResultBuilder<Model> resultBuilder = Result.builder();
        return resultBuilder.success(success).code(code).message(message).model(model).build();
    }

    public static <Model> Result<Model> getResult(ResultEnum resultEnum, Model model) {
        return getResult(resultEnum.getSuccess(), resultEnum.getCode(), resultEnum.getMessage(), model);
    }

    public static <Model> Result<Model> success(Model model) {
        return getResult(ResultEnum.SUCCESS, model);
    }

    public static Result<String> success() {
        return success("");
    }

    public static <Model> Result<Model> unknownError(Model model) {
        return getResult(ResultEnum.UNKNOWN_ERROR, model);
    }

    public static Result<String> unknownError() {
        return unknownError("");
    }

    public static <Model> Result<Model> paramError(Model model) {
        return getResult(ResultEnum.PARAM_ERROR, model);
    }

    public static Result<String> paramError() {
        return paramError("");
    }

    public static <Model> Result<Model> nullPointError(Model model) {
        return getResult(ResultEnum.NULL_POINT, model);
    }

    public static Result<String> nullPointError() {
        return nullPointError("");
    }

    public static <Model> Result<Model> clientError(Model model) {
        return getResult(ResultEnum.HTTP_CLIENT_ERROR, model);
    }

    public static Result<String> clientError() {
        return clientError("");
    }

    public static <Model> Result<Model> error(Integer code, String message, Model model) {
        return getResult(false, code, message, model);
    }

    public static Result<String> error(Integer code, String message) {
        return error(code, message, "");
    }
}
