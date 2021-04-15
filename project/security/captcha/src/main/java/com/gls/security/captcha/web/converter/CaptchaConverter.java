package com.gls.security.captcha.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.security.captcha.web.entity.CaptchaEntity;
import com.gls.security.captcha.web.model.CaptchaModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class CaptchaConverter extends BaseConverter<CaptchaEntity, CaptchaModel> {

    @Override
    protected CaptchaModel copySourceToTarget(CaptchaEntity captchaEntity) {
        CaptchaModel model = new CaptchaModel();
        model.setCode(captchaEntity.getCode());
        model.setExpireTime(captchaEntity.getExpireTime());
        return model;
    }

    @Override
    protected CaptchaEntity copyTargetToSource(CaptchaModel captchaModel) {
        CaptchaEntity entity = new CaptchaEntity();
        entity.setCode(captchaModel.getCode());
        entity.setExpireTime(captchaModel.getExpireTime());
        return entity;
    }
}
