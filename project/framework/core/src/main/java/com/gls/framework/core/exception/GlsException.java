package com.gls.framework.core.exception;

import lombok.Data;

/**
 * @author george
 */
@Data
public class GlsException extends RuntimeException {
    private Integer code = 500;
    private String message;

    public GlsException() {
        super();
    }

    public GlsException(String message) {
        super(message);
        this.message = message;
    }

    public GlsException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public GlsException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public GlsException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }
}
