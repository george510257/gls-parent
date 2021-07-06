package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ExtractCheckModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ExtractCheckValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ExtractCheckModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtractCheckModel model = (ExtractCheckModel) target;
        // todo
    }
}
