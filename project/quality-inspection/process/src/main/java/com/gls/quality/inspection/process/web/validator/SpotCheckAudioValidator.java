package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class SpotCheckAudioValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SpotCheckAudioModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpotCheckAudioModel model = (SpotCheckAudioModel) target;
        // todo
    }
}
