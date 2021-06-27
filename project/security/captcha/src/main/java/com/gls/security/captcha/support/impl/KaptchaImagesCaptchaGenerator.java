package com.gls.security.captcha.support.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.support.ImagesCaptchaGenerator;
import com.gls.security.captcha.web.model.ImagesCaptchaModel;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author george
 */
@Slf4j
@AllArgsConstructor
public class KaptchaImagesCaptchaGenerator implements ImagesCaptchaGenerator {
    private final CaptchaProperties captchaProperties;

    @Override
    public ImagesCaptchaModel generate() {
        CaptchaProperties.Images images = captchaProperties.getImages();
        DefaultKaptcha defaultKaptcha = getDefaultKaptcha(images);
        String text = defaultKaptcha.createText();
        log.info("text: {}", text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ImagesCaptchaModel imagesCaptcha = new ImagesCaptchaModel();
        imagesCaptcha.setCode(text);
        imagesCaptcha.setImages(image);
        imagesCaptcha.setExpireTime(LocalDateTime.now().plusSeconds(images.getExpireIn()));
        return imagesCaptcha;
    }

    private DefaultKaptcha getDefaultKaptcha(CaptchaProperties.Images images) {
        Properties properties = new Properties();
        // 是否使用边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 验证码图片的宽度
        properties.setProperty("kaptcha.image.width", String.valueOf(images.getWidth()));
        // 验证码图片的高度
        properties.setProperty("kaptcha.image.height", String.valueOf(images.getHeight()));
        // 验证码字体的大小
        properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(images.getFontSize()));
        // 验证码输出的字符长度
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(images.getLength()));
        // 验证码的字体设置
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 验证码的取值范围
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
        // 图片的样式
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        // 干扰颜色，合法值： r,g,b 或者 white,black,blue.
        properties.setProperty("kaptcha.noise.color", "black");
        // 干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        // 背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "185,56,213");
        // 背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
        // 文字间隔
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
