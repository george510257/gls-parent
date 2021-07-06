package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ModelResultModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ModelResultValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ModelResultModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ModelResultModel model = (ModelResultModel) target;
        // todo
    }
}
