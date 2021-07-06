package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CheckModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CheckValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CheckModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CheckModel model = (CheckModel) target;
        // todo
    }
}
