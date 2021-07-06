package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ConfigChannelModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ConfigChannelValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigChannelModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigChannelModel model = (ConfigChannelModel) target;
        // todo
    }
}
