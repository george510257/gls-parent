package com.gls.security.captcha.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImagesCaptchaModel extends CaptchaModel {

    private transient BufferedImage images;
}
