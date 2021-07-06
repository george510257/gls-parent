package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.SpecialLabelModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class SpecialLabelValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SpecialLabelModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialLabelModel model = (SpecialLabelModel) target;
        // todo
    }
}
