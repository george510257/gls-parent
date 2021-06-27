package com.gls.security.captcha.web.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author george
 */
@Data
public class CaptchaModel implements Serializable {
    private String code;
    private LocalDateTime expireTime;
}
