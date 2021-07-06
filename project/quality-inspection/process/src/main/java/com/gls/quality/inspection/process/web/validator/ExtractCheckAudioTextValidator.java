package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ExtractCheckAudioTextValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ExtractCheckAudioTextModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtractCheckAudioTextModel model = (ExtractCheckAudioTextModel) target;
        // todo
    }
}
