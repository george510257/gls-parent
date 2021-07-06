package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ExtractCheckResultTextModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ExtractCheckResultTextValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ExtractCheckResultTextModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtractCheckResultTextModel model = (ExtractCheckResultTextModel) target;
        // todo
    }
}
