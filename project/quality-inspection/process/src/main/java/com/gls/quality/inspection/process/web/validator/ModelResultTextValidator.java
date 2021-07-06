package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ModelResultTextModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ModelResultTextValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ModelResultTextModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ModelResultTextModel model = (ModelResultTextModel) target;
        // todo
    }
}
