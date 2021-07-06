package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ExtractCheckAudioModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtractCheckAudioModel model = (ExtractCheckAudioModel) target;
        // todo
    }
}
