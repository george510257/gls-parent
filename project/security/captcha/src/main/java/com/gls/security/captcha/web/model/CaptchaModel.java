package com.gls.security.captcha.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class CaptchaModel implements Serializable {
    private String code;
    private LocalDateTime expireTime;
}
