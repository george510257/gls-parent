package com.gls.security.captcha.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.security.captcha.web.entity.CaptchaEntity;
import com.gls.security.captcha.web.model.CaptchaModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CaptchaConverter implements BaseConverter<CaptchaEntity, CaptchaModel> {
    @Override
    public CaptchaModel copySourceToTarget(CaptchaEntity captchaEntity, CaptchaModel captchaModel) {
        captchaModel.setCode(captchaEntity.getCode());
        captchaModel.setExpireTime(captchaEntity.getExpireTime());
        return captchaModel;
    }

    @Override
    public CaptchaEntity copyTargetToSource(CaptchaModel captchaModel, CaptchaEntity captchaEntity) {
        captchaEntity.setCode(captchaModel.getCode());
        captchaEntity.setExpireTime(captchaModel.getExpireTime());
        return captchaEntity;
    }
}
