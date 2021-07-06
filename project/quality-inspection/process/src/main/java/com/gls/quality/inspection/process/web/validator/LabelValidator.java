package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.LabelModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class LabelValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LabelModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LabelModel model = (LabelModel) target;
        // todo
    }
}
