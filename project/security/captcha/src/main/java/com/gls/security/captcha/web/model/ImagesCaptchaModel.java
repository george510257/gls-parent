package com.gls.security.captcha.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.awt.image.BufferedImage;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ImagesCaptchaModel extends CaptchaModel {
    private transient BufferedImage images;
}
