package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ConfigInvalidAudioModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ConfigInvalidAudioValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigInvalidAudioModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigInvalidAudioModel model = (ConfigInvalidAudioModel) target;
        // todo
    }
}