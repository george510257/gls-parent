package com.gls.security.captcha.support.impl;

import com.gls.security.captcha.constants.CaptchaProperties;
import com.gls.security.captcha.support.ImagesCaptchaGenerator;
import com.gls.security.captcha.web.model.ImagesCaptchaModel;
import com.gls.starter.web.support.ServletHelper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.ServletRequestUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 默认的图片验证码生成器
 *
 * @author george
 */
@AllArgsConstructor
public class DefaultImagesCaptchaGenerator implements ImagesCaptchaGenerator {

    private final CaptchaProperties captchaProperties;

    @Override
    public ImagesCaptchaModel generate() {
        CaptchaProperties.Images images = captchaProperties.getImages();
        ImagesCaptchaModel imagesCaptcha = new ImagesCaptchaModel();

        int width = ServletRequestUtils.getIntParameter(ServletHelper.getRequest(), "width", images.getWidth());
        int height = ServletRequestUtils.getIntParameter(ServletHelper.getRequest(), "height", images.getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, images.getFontSize()));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < images.getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        imagesCaptcha.setImages(image);
        imagesCaptcha.setCode(sRand.toString());
        imagesCaptcha.setExpireTime(LocalDateTime.now().plusSeconds(images.getExpireIn()));

        return imagesCaptcha;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
