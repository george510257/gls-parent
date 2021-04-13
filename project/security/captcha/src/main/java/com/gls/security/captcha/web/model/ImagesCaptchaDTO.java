package com.gls.security.captcha.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.image.BufferedImage;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImagesCaptchaDTO extends CaptchaDTO {

    @JsonIgnore
    private transient BufferedImage images;
}
