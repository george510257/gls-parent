package com.gls.framework.core.exception;

/**
 * @author george
 */
public class DefaultException extends Exception {

    private String code;

    private String message;

    public DefaultException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DefaultException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public DefaultException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
