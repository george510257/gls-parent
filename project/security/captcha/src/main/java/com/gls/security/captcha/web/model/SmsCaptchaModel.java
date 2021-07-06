package com.gls.security.captcha.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SmsCaptchaModel extends CaptchaModel {
}
