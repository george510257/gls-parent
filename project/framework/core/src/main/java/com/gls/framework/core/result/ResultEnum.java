package com.gls.framework.core.result;

/**
 * @author george
 */
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(true, 20000, "成功"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(false, 20001, "未知错误"),

    /**
     * 参数错误
     */
    PARAM_ERROR(false, 20002, "参数错误"),

    /**
     * 空指针异常
     */
    NULL_POINT(false, 20003, "空指针异常"),

    /**
     * 客户端连接异常
     */
    HTTP_CLIENT_ERROR(false, 20004, "客户端连接异常");

    private Boolean success;

    private Integer code;

    private String message;

    ResultEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
