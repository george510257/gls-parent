package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class SpotCheckValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SpotCheckModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpotCheckModel model = (SpotCheckModel) target;
        // todo
    }
}
