package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ModelModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ModelValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ModelModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ModelModel model = (ModelModel) target;
        // todo
    }
}
